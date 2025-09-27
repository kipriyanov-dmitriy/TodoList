package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.ui.components.TaskItem
import com.example.todo.ui.contract.TodoListContract
import com.example.todo.ui.model.TaskUiModel

@Composable
fun ArchiveContent(
    state: TaskUiModel,
    sendIntent: (TodoListContract.Intent) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            items(items = state.archiveListOfTasks, key = { it.id }) { item ->
                TaskItem(
                    state = item,
                )
            }
        }
    }
}