package com.example.todo.ui.contract

import com.example.todo.ui.model.TaskItemUiModel

class TodoListContract {
    sealed interface Intent {
        data class OnTransferringItemInWorkTab(val item: TaskItemUiModel) : Intent
        data class OnTransferringItemArchive(val item: TaskItemUiModel) : Intent
        data class OnTransferringItemBacklog(val item: TaskItemUiModel) : Intent
    }
}