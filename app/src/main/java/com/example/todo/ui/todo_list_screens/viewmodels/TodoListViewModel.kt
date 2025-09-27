package com.example.todo.ui.todo_list_screens.viewmodels

import com.example.todo.core.CoroutineViewModel
import com.example.todo.domain.model.StorageStatus
import com.example.todo.domain.usecases.ChangeTaskStatusUseCase
import com.example.todo.domain.usecases.GetListOfTasksUseCase
import com.example.todo.ui.contract.TodoListContract.Intent
import com.example.todo.ui.contract.TodoListContract.Intent.OnTransferringItemInWorkTab
import com.example.todo.ui.model.TaskItemUiModel
import com.example.todo.ui.model.TaskUiModel
import com.example.todo.ui.model.toTaskItemUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val getListOfTasksUseCase: GetListOfTasksUseCase,
    private val changeTaskStatusUseCase: ChangeTaskStatusUseCase,
) : CoroutineViewModel() {
    private val _viewState: MutableStateFlow<TaskUiModel> = MutableStateFlow(TaskUiModel())
    val viewState: StateFlow<TaskUiModel> = _viewState.asStateFlow()

    init {
        launch(Dispatchers.IO) {
            getListOfTasksUseCase.invoke().collect { tasks ->
                _viewState.value = TaskUiModel(
                    inProgressListOfTasks = tasks.filter { it.storageStatus == StorageStatus.IN_PROGRESS }
                        .map { it.toTaskItemUiModel() },
                    backlogListOfTasks = tasks.filter { it.storageStatus == StorageStatus.BACKLOG }
                        .map { it.toTaskItemUiModel() },
                    archiveListOfTasks = tasks.filter { it.storageStatus == StorageStatus.ARCHIVE }
                        .map { it.toTaskItemUiModel() }
                )
            }
        }
    }

    fun handleIntent(intent: Intent) {
        when (intent) {
            is OnTransferringItemInWorkTab -> {
                onTransferringItemInWork(intent.item)
            }

            is Intent.OnTransferringItemArchive -> {
                onTransferringItemArchive(intent.item)
            }

            is Intent.OnTransferringItemBacklog -> {
                onTransferringItemBacklog(intent.item)
            }
        }
    }

    private fun onTransferringItemBacklog(item: TaskItemUiModel) {
        launch { changeTaskStatusUseCase(item.id, StorageStatus.BACKLOG) }
    }

    private fun onTransferringItemArchive(item: TaskItemUiModel) {
        launch { changeTaskStatusUseCase(item.id, StorageStatus.ARCHIVE) }
    }

    private fun onTransferringItemInWork(item: TaskItemUiModel) {
        launch {
            changeTaskStatusUseCase(item.id, StorageStatus.IN_PROGRESS)
        }
    }
}