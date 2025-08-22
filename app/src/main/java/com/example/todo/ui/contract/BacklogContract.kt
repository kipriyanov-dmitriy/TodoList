package com.example.todo.ui.contract

import com.example.todo.ui.model.TaskItemUiModel

class BacklogContract {
    interface Intent {
        data class OnItemRemove(val item: TaskItemUiModel) : Intent
        data class OnAddedTask(val item: TaskItemUiModel) : Intent
        data object UpdateBacklogList: Intent
    }
}