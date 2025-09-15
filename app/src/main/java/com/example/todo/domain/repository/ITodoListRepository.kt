package com.example.todo.domain.repository

import com.example.todo.domain.model.StorageStatus
import com.example.todo.domain.model.TaskItem
import kotlinx.coroutines.flow.Flow

interface ITodoListRepository {
    fun getListOfTasks(): Flow<List<TaskItem>>
    suspend fun addedTaskItem(item: TaskItem)
    suspend fun changeTaskStatus(taskId: Long, status: StorageStatus)
}