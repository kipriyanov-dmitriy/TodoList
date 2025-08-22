package com.example.todo.ui.contract

import com.example.todo.ui.components.Priority
import java.time.LocalDate

class AddedTaskContract {
    sealed interface Intent {
        data object OnCreateTask : Intent
        data class OnTitleValueChange(val value: String) : Intent
        data class OnDescriptionValueChange(val value: String) : Intent
        data class OnPriorityValueChange(val priority: Priority) : Intent
        data class OnDeadlineDateValueChange(val date: LocalDate) : Intent
    }

    sealed interface Effect {
        data object OnTaskCreated : Effect
    }
}