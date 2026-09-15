package com.productivityapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import com.productivityapp.database.EdgeEntity
import com.productivityapp.ui.components.Task

@Composable
fun KnowledgeGraphCanvas(tasks: List<Task>, edges: List<EdgeEntity>, onNodeTapped: (String) -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale *= zoomChange
        offset += panChange
    }

    // Mock node coordinate assignment logic based on hashcode for spatial mapping
    fun getNodeCoordinate(taskId: String): Offset {
        val hash = taskId.hashCode()
        val x = (hash % 1000).toFloat()
        val y = ((hash / 1000) % 1000).toFloat()
        return Offset(x + 300f, y + 500f) // Centered arbitrarily for demo
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = transformableState)
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val adjustedTap = (tapOffset - offset) / scale
                    tasks.forEach { task ->
                        val nodePos = getNodeCoordinate(task.id)
                        // Distance check for tap
                        if ((adjustedTap - nodePos).getDistance() < 50f) {
                            onNodeTapped(task.id)
                        }
                    }
                }
            }
    ) {
        // Apply transformations
        drawIntoCanvas {
            it.translate(offset.x, offset.y)
            it.scale(scale, scale)

            // Draw Edges
            edges.forEach { edge ->
                drawLine(
                    color = Color.LightGray,
                    start = getNodeCoordinate(edge.sourceId),
                    end = getNodeCoordinate(edge.targetId),
                    strokeWidth = 3f
                )
            }

            // Draw Nodes
            tasks.forEach { task ->
                val pos = getNodeCoordinate(task.id)
                drawCircle(
                    color = if (task.completed) Color(0xFF4CAF50) else Color(0xFF2196F3),
                    radius = 40f,
                    center = pos
                )
                
                // Draw text labels
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 30f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                it.nativeCanvas.drawText(
                    task.title.take(15) + if (task.title.length > 15) "..." else "",
                    pos.x,
                    pos.y - 50f,
                    paint
                )
            }
        }
    }
}
