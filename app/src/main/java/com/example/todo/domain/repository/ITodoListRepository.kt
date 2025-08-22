package com.example.todo.domain.repository

import com.example.todo.domain.model.TaskItem

interface ITodoListRepository {
    suspend fun getListOfBackLogTasks(): List<TaskItem>
    suspend fun addedTaskItem(item: TaskItem)
}