package com.example.todo.domain.usecases

import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.repository.ITodoListRepository

class AddedTaskItemUseCase(private val repo: ITodoListRepository) {
    suspend operator fun invoke(item: TaskItem){
        repo.addedTaskItem(item)
    }
}