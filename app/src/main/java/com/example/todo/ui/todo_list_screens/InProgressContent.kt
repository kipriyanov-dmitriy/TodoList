package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.todo.ui.components.SwipeConfig
import com.example.todo.ui.components.TaskItem
import com.example.todo.ui.contract.TodoListContract
import com.example.todo.ui.model.TaskItemUiModel
import com.example.todo.ui.model.TaskUiModel
import com.example.todo.ui.theme.Blue
import com.example.todo.ui.theme.Orange
import kotlinx.coroutines.launch

@Composable
fun InProgressContent(
    state: TaskUiModel,
    sendIntent: (TodoListContract.Intent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val coroutineScope = rememberCoroutineScope()
    var lastDeleteTaskItem by remember { mutableStateOf<TaskItemUiModel?>(null) }
    var isItemVisible by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            items(items = state.inProgressListOfTasks, key = { it.id }) { item ->
                TaskItem(
                    state = item,
                    swipeToLeft = SwipeConfig.Enabled(
                        text = "В архив",
                        backgroundColor = Blue,
                        onSwiped = {
                            sendIntent(TodoListContract.Intent.OnTransferringItemArchive(item))
                            lastDeleteTaskItem = item
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Задача \"${item.title}\" перенесена в архив",
                                    duration = SnackbarDuration.Short,
                                    actionLabel = "Отмена",

                                    )
                                if (result == SnackbarResult.ActionPerformed) {
                                    lastDeleteTaskItem?.let {
                                        sendIntent(
                                            TodoListContract.Intent.OnTransferringItemInWorkTab(it)
                                        )
                                    }
                                    isItemVisible = true
                                    lastDeleteTaskItem = null
                                }
                            }
                        }
                    ),
                    swipeToRight = SwipeConfig.Enabled(
                        text = "В бэклог",
                        backgroundColor = Orange,
                        onSwiped = {
                            sendIntent(TodoListContract.Intent.OnTransferringItemBacklog(item))
                        }
                    ),
                    onVisibilityValueChanged = { isItemVisible }
                )
            }
        }
    }
}