package com.productivityapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class SchedulingService(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleTaskReminder(taskId: String, delayMillis: Long) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("TASK_ID", taskId)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + delayMillis,
            pendingIntent
        )
    }
}

class ReminderReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getStringExtra("TASK_ID")
        // Trigger high-priority Heads-up notification here based on taskId
        println("ReminderReceiver: Notification triggered for task $taskId")
    }
}
