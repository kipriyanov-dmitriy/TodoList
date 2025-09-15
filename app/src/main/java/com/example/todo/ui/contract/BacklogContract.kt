package com.example.todo.ui.contract

import com.example.todo.ui.model.TaskItemUiModel

class BacklogContract {
    sealed interface Intent {
        data class OnTransferringItemInWorkTab(val item: TaskItemUiModel) : Intent
        data class OnTransferringItemArchive(val item: TaskItemUiModel) : Intent
    }
}