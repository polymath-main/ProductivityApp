package com.productivityapp.services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

// Feature 1: Contextual Semantic Search (Mock)
object SemanticSearchEngine {
    // A simple mock that randomizes results to simulate "semantic" understanding or filters by some fuzzy logic
    fun search(query: String, tasks: List<com.productivityapp.ui.components.Task>): List<com.productivityapp.ui.components.Task> {
        if (query.isBlank()) return tasks
        // Mock semantic matching: just return items where title contains any word from query, ignoring case
        val queryWords = query.lowercase().split(" ")
        return tasks.filter { task ->
            val taskWords = task.title.lowercase().split(" ")
            queryWords.any { qw -> taskWords.any { tw -> tw.contains(qw) || qw.contains(tw) } }
        }
    }
}

// Feature 2: AR Spatial Task Mapping (Mock)
object ARSpatialMapper {
    fun pinTaskInEnvironment(taskId: String): String {
        val x = Random.nextInt(-10, 10)
        val y = Random.nextInt(0, 5)
        val z = Random.nextInt(-5, 5)
        return "Pinned Task $taskId at Spatial Anchor(x=$x, y=$y, z=$z)"
    }
}

// Feature 3: Biometric Cognitive Load Tracking (Mock)
object BiometricTracker {
    val cognitiveLoadFlow: Flow<Float> = flow {
        var currentLoad = 0.5f // Start at 50%
        while (true) {
            // Randomly fluctuate the load between 0.0 and 1.0
            val delta = (Random.nextFloat() * 0.2f) - 0.1f
            currentLoad = (currentLoad + delta).coerceIn(0.0f, 1.0f)
            emit(currentLoad)
            delay(2000L) // Update every 2 seconds
        }
    }
}
