package com.example.todo.ui.todo_list_screens.viewmodels

import com.example.todo.core.CoroutineViewModel
import com.example.todo.core.TodoDateUtils
import com.example.todo.domain.usecases.GetListOfBacklogTasksUseCase
import com.example.todo.ui.components.getPriority
import com.example.todo.ui.contract.BacklogContract.Intent
import com.example.todo.ui.contract.BacklogContract.Intent.OnItemRemove
import com.example.todo.ui.contract.BacklogContract.Intent.OnAddedTask
import com.example.todo.ui.model.TaskItemUiModel
import com.example.todo.ui.model.TaskUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val getListOfBacklogTasksUseCase: GetListOfBacklogTasksUseCase,
) : CoroutineViewModel() {
    private val _viewState: MutableStateFlow<TaskUiModel> = MutableStateFlow(
        TaskUiModel(listOfTasks = emptyList())
    )
    val viewState: StateFlow<TaskUiModel> = _viewState.asStateFlow()

    init {
        updateBacklogList()
    }

    fun handleIntent(intent: Intent) {
        when (intent) {
            is OnItemRemove -> {
                onItemRemove(intent.item)
            }

            is OnAddedTask -> {
                onAddedTask(intent.item)
            }

            Intent.UpdateBacklogList -> {
                updateBacklogList()
            }
        }
    }

    private fun updateBacklogList() {
        launch {
            val list = getListOfBacklogTasksUseCase.invoke().map { item ->
                TaskItemUiModel(
                    id = item.id,
                    title = item.title,
                    description = item.description,
                    dateOfCreate = TodoDateUtils.formatDate(item.dateOfCreate),
                    priority = item.businessPriority.getPriority(),
                    storageStatus = item.storageStatus
                )
            }

            _viewState.update {
                it.copy(
                    listOfTasks = list
                )
            }
        }
    }

    private fun onAddedTask(item: TaskItemUiModel) {

    }

    private fun onItemRemove(item: TaskItemUiModel) {

    }
}