package com.productivityapp.services

import com.productivityapp.database.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OmniFlow {
    /**
     * Dynamically reorders tasks based on user's energy and productivity peaks.
     * @param tasks List of TaskEntity objects
     * @param currentHour The current hour of the day (0-23)
     */
    suspend fun optimizeTaskFlow(tasks: List<TaskEntity>, currentHour: Int): List<TaskEntity> = withContext(Dispatchers.Default) {
        val isPeakEnergy = (currentHour in 9..11) || (currentHour in 15..17)
        
        val reorderedTasks = if (isPeakEnergy) {
            tasks.sortedByDescending { it.priority }
        } else {
            tasks.sortedBy { it.priority }
        }

        // TODO: Log the optimization event (omni_flow_reorder)
        
        reorderedTasks
    }
}
