package com.productivityapp.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "knowledge_edges",
    indices = [Index("sourceId"), Index("targetId")]
)
data class EdgeEntity(
    @PrimaryKey(autoGenerate = true) val edgeId: Long = 0,
    val sourceId: String,
    val targetId: String,
    val relationType: String,
    val timestamp: Long = System.currentTimeMillis()
)
