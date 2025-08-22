package com.example.todo.data.repository

import com.example.todo.data.db.BacklogTasksDAO
import com.example.todo.data.mappers.toBacklogTaskItem
import com.example.todo.data.mappers.toTaskItemEntity
import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.repository.ITodoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoListRepositoryImpl(
    private val dao: BacklogTasksDAO,
) : ITodoListRepository {
    override suspend fun getListOfBackLogTasks(): List<TaskItem> = withContext(Dispatchers.IO) {
        dao.getAllBacklogTask().map { it.toBacklogTaskItem() }
    }

    override suspend fun addedTaskItem(item: TaskItem) {
        withContext(Dispatchers.IO) {
            dao.insertTask(item.toTaskItemEntity())
        }
    }
}