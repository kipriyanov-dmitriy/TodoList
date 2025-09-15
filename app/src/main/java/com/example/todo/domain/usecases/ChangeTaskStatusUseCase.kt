package com.example.todo.domain.usecases

import com.example.todo.domain.model.StorageStatus
import com.example.todo.domain.repository.ITodoListRepository

class ChangeTaskStatusUseCase(private val repo: ITodoListRepository) {
    suspend operator fun invoke(taskId: Long, status: StorageStatus) {
        repo.changeTaskStatus(taskId, status)
    }
}