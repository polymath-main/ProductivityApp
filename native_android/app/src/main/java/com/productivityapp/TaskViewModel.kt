package com.productivityapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.productivityapp.database.DatabaseProvider
import com.productivityapp.database.TaskEntity
import com.productivityapp.ui.components.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = DatabaseProvider.getDatabase(application).taskDao()

    val tasks = dao.getAllTasks().map { entityList ->
        entityList.map { 
            Task(it.id, it.title, it.priority, it.completed) 
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTask(task: Task) {
        viewModelScope.launch {
            dao.insertTask(TaskEntity(task.id, task.title, task.priority, task.completed))
        }
    }

    fun toggleTask(id: String, currentStatus: Boolean) {
        viewModelScope.launch {
            dao.updateTaskStatus(id, !currentStatus)
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            dao.deleteTask(id)
        }
    }
}
