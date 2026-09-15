package com.productivityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.productivityapp.services.BiometricTracker
import com.productivityapp.ui.components.Task
import com.productivityapp.ui.components.TaskList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainAppScreen()
                }
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    val cognitiveLoad by BiometricTracker.cognitiveLoadFlow.collectAsState(initial = 0.5f)

    var tasks by remember {
        mutableStateOf(
            listOf(
                Task("1", "Analyze user feedback", "high", false),
                Task("2", "Draft quarterly roadmap", "medium", false),
                Task("3", "Review pull requests", "low", true)
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Feature 3: Biometric Cognitive Load Tracking UI
        val loadColor = when {
            cognitiveLoad > 0.8f -> MaterialTheme.colorScheme.error
            cognitiveLoad > 0.5f -> Color(0xFFFFA500) // Orange
            else -> Color(0xFF4CAF50) // Green
        }
        
        val loadText = when {
            cognitiveLoad > 0.8f -> "High Cognitive Load - Take a break!"
            cognitiveLoad > 0.5f -> "Medium Load - Stay focused"
            else -> "Low Load - Optimal state"
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(loadColor.copy(alpha = 0.2f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Neural Biometrics: ${"%.0f".format(cognitiveLoad * 100)}% | $loadText",
                color = loadColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // Feature 1 & 2 integration in TaskList
        TaskList(
            tasks = tasks,
            onToggleTask = { id ->
                tasks = tasks.map { if (it.id == id) it.copy(completed = !it.completed) else it }
            },
            onDeleteTask = { id ->
                tasks = tasks.filter { it.id != id }
            },
            onDecomposeTask = { id ->
                // Mock decompose action
            }
        )
    }
}
