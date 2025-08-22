package com.example.todo.domain.usecases

import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.repository.ITodoListRepository

class GetListOfBacklogTasksUseCase(
    private val repo: ITodoListRepository
) {
    suspend operator fun invoke(): List<TaskItem> =
        repo.getListOfBackLogTasks()
}