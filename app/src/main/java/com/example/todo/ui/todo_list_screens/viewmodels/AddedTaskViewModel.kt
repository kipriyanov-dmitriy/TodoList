package com.example.todo.ui.todo_list_screens.viewmodels

import com.example.todo.core.CoroutineViewModel
import com.example.todo.domain.usecases.AddedTaskItemUseCase
import com.example.todo.ui.contract.AddedTaskContract
import com.example.todo.ui.contract.AddedTaskContract.Intent
import com.example.todo.ui.contract.AddedTaskContract.Intent.OnCreateTask
import com.example.todo.ui.model.AddedTaskUiModel
import com.example.todo.ui.model.toTaskItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddedTaskViewModel(
    private val addedTaskItemUseCase: AddedTaskItemUseCase,
) : CoroutineViewModel() {
    private val state: MutableStateFlow<AddedTaskUiModel> = MutableStateFlow(
        AddedTaskUiModel()
    )

    private val _effect: MutableSharedFlow<AddedTaskContract.Effect> = MutableSharedFlow()
    val effect: SharedFlow<AddedTaskContract.Effect> = _effect.asSharedFlow()

    fun handleIntent(intent: Intent) {
        when (intent) {
            OnCreateTask -> {
                onCreateTask()
            }

            is Intent.OnDeadlineDateValueChange -> {
                state.value = state.value.copy(dateOfCreate = intent.date)
            }

            is Intent.OnDescriptionValueChange -> {
                state.value = state.value.copy(description = intent.value)
            }

            is Intent.OnPriorityValueChange -> {
                state.value = state.value.copy(priority = intent.priority)
            }

            is Intent.OnTitleValueChange -> {
                state.value = state.value.copy(title = intent.value)
            }
        }
    }

    private fun onCreateTask() {
        launch {
            addedTaskItemUseCase.invoke(state.value.toTaskItem())
            _effect.emit(AddedTaskContract.Effect.OnTaskCreated)
        }
    }
}