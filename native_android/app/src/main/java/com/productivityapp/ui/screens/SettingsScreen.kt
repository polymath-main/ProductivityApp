package com.productivityapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var smartScheduling by remember { mutableStateOf(true) }
    var workManagerSync by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productivity Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Core Preferences", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Global Notifications", fontWeight = FontWeight.SemiBold)
                        Text("Enable or disable all app alerts and reminders.", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Smart Scheduling", fontWeight = FontWeight.SemiBold)
                        Text("Let OmniFlow suggest optimal deep-work periods.", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = smartScheduling, onCheckedChange = { smartScheduling = it })
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Background Sync (WorkManager)", fontWeight = FontWeight.SemiBold)
                        Text("Periodically recalculate graph edges and optimize DB offline.", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = workManagerSync, onCheckedChange = { workManagerSync = it })
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save & Exit")
            }
        }
    }
}
