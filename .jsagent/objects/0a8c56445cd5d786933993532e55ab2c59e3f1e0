package com.productivityapp.services

import com.productivityapp.database.EdgeDao
import com.productivityapp.database.EdgeEntity
import kotlinx.coroutines.flow.Flow

class PKMGraphService(private val edgeDao: EdgeDao) {
    
    suspend fun createLink(sourceId: String, targetId: String, relation: String = "REFERENCES") {
        edgeDao.insertEdge(EdgeEntity(
            sourceId = sourceId,
            targetId = targetId,
            relationType = relation
        ))
    }

    fun getBacklinks(nodeId: String): Flow<List<EdgeEntity>> {
        return edgeDao.getBacklinks(nodeId)
    }
}
