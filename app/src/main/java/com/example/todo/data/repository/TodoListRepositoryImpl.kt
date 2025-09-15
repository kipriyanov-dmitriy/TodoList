package com.example.todo.data.repository

import com.example.todo.data.db.TasksDAO
import com.example.todo.data.mappers.toTaskItem
import com.example.todo.data.mappers.toTaskItemEntity
import com.example.todo.domain.model.StorageStatus
import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.repository.ITodoListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoListRepositoryImpl(
    private val dao: TasksDAO,
) : ITodoListRepository {
    override fun getListOfTasks(): Flow<List<TaskItem>> =
        dao.getAllTaskFlow().map {
            it.map { itemEntity -> itemEntity.toTaskItem() }
        }


    override suspend fun addedTaskItem(item: TaskItem) {
        withContext(Dispatchers.IO) {
            dao.insertTask(item.toTaskItemEntity())
        }
    }

    override suspend fun changeTaskStatus(taskId: Long, status: StorageStatus) {
        withContext(Dispatchers.IO) {
            dao.updateTaskById(taskId, status)
        }
    }
}