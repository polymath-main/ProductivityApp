package com.productivityapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroTimer(tasks: List<Task> = emptyList()) {
    val focusTime = 25 * 60
    val breakTime = 5 * 60

    var timeLeft by remember { mutableStateOf(focusTime) }
    var isActive by remember { mutableStateOf(false) }
    var isFocusMode by remember { mutableStateOf(true) }
    var selectedTaskId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isActive, timeLeft) {
        if (isActive && timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        } else if (isActive && timeLeft == 0) {
            isFocusMode = !isFocusMode
            timeLeft = if (isFocusMode) focusTime else breakTime
            isActive = false
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val breathScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isActive) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isFocusMode) "Focus Session" else "Break Time",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val pendingTasks = tasks.filter { !it.completed }
        if (isFocusMode && pendingTasks.isNotEmpty()) {
            Text("Focusing on:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(modifier = Modifier.padding(bottom = 24.dp)) {
                items(pendingTasks) { task ->
                    FilterChip(
                        selected = selectedTaskId == task.id,
                        onClick = { selectedTaskId = task.id },
                        label = { Text(task.title) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(260.dp)
                .scale(breathScale)
                .border(
                    width = 4.dp,
                    color = if (isFocusMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            val minutes = timeLeft / 60
            val seconds = timeLeft % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                fontSize = 72.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Button(
                onClick = { isActive = !isActive },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(if (isActive) "Pause" else "Start")
            }
            OutlinedButton(
                onClick = {
                    isActive = false
                    timeLeft = if (isFocusMode) focusTime else breakTime
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Reset")
            }
        }
    }
}
