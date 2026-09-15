package com.productivityapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSchedulingBottomSheet(
    taskId: String,
    taskTitle: String,
    onDismiss: () -> Unit,
    onSchedule: (delayMillis: Long) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text("Schedule Task", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(taskTitle, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Mock choices for simplicity in the UI blueprint
            val options = listOf(
                "In 10 Seconds (Demo)" to 10_000L,
                "In 5 Minutes" to 5 * 60_000L,
                "Tomorrow Morning" to 12 * 3600_000L
            )
            
            var selectedOption by remember { mutableStateOf(options[0]) }
            
            options.forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(option.first, modifier = Modifier.padding(start = 8.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { 
                    onSchedule(selectedOption.second)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Schedule")
            }
        }
    }
}
