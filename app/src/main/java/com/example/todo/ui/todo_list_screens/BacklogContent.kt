package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.core.PreviewPhone
import com.example.todo.domain.model.StorageStatus
import com.example.todo.ui.components.Priority
import com.example.todo.ui.components.TaskItem
import com.example.todo.ui.contract.BacklogContract
import com.example.todo.ui.model.TaskItemUiModel
import com.example.todo.ui.model.TaskUiModel
import com.example.todo.ui.theme.TodoTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Composable
fun BacklogContent(
    state: TaskUiModel,
    sendIntent: (BacklogContract.Intent) -> Unit,
    onAddNoteClick: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        println(state.backlogListOfTasks)
        LazyColumn {
            items(items = state.backlogListOfTasks, key = { it.id }) { item ->
                TaskItem(
                    state = item,
                    onTransferringSwipe = {
                        sendIntent(BacklogContract.Intent.OnTransferringItemInWorkTab(item))
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = onAddNoteClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить")
        }
    }
}

@Composable
@PreviewPhone
private fun BacklogScreenPreview() {
    TodoTheme {
        val date = remember {
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
        val list = List(15) {
            TaskItemUiModel(
                id = Random.nextLong(),
                title = "title_$it",
                description = "description description description description",
                dateOfCreate = date,
                priority = Priority.entries[Random.nextInt(0, 3)],
                storageStatus = StorageStatus.BACKLOG
            )
        }
        BacklogContent(
            state = TaskUiModel(
                backlogListOfTasks = list
            ),
            sendIntent = {}
        ) { }
    }
}