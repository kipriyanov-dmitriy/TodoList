package com.example.todo.domain.usecases

import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.repository.ITodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class GetListOfTasksUseCase(
    private val repo: ITodoListRepository
) {
    operator fun invoke(): Flow<List<TaskItem>> =
        repo.getListOfTasks()
}