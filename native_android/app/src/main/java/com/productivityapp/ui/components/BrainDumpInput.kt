package com.productivityapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@Composable
fun BrainDumpInput(onTasksParsed: (List<Task>) -> Unit) {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Brain Dump",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Type everything on your mind. We will sort it out.",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 16.dp),
            placeholder = { Text("e.g. Need to call mom, finish the report...") },
            shape = RoundedCornerShape(12.dp)
        )

        Button(
            onClick = {
                if (text.isNotBlank()) {
                    val task = Task(
                        id = UUID.randomUUID().toString(),
                        title = text.trim(),
                        priority = "medium",
                        completed = false
                    )
                    onTasksParsed(listOf(task))
                    text = ""
                }
            },
            enabled = text.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Process Thoughts ✨", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
