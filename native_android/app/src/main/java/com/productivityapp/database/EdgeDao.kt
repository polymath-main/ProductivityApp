package com.productivityapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EdgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEdge(edge: EdgeEntity)

    @Query("SELECT * FROM knowledge_edges WHERE targetId = :nodeId")
    fun getBacklinks(nodeId: String): Flow<List<EdgeEntity>>
    
    @Query("SELECT * FROM knowledge_edges WHERE sourceId = :nodeId")
    fun getForwardLinks(nodeId: String): Flow<List<EdgeEntity>>
}
