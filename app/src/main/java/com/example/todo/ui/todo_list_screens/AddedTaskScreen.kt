package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.core.PreviewPhone
import com.example.todo.ui.components.DeadlineDateItem
import com.example.todo.ui.components.DescriptionTextField
import com.example.todo.ui.components.PriorityItem
import com.example.todo.ui.components.TitleTextField
import com.example.todo.ui.contract.AddedTaskContract
import com.example.todo.ui.contract.AddedTaskContract.Intent.OnCreateTask
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.todo_list_screens.viewmodels.AddedTaskViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackPressed: () -> Unit,
) {
    val viewModel: AddedTaskViewModel = koinViewModel()
    val sendIntent by remember { mutableStateOf(viewModel::handleIntent) }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AddedTaskContract.Effect.OnTaskCreated -> {
                    onBackPressed()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Новая заметка") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            sendIntent(OnCreateTask)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_save_36),
                            contentDescription = "Кнопка сохранения заметки"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TitleTextField { title ->
                sendIntent(AddedTaskContract.Intent.OnTitleValueChange(title))
            }
            DescriptionTextField { description ->
                sendIntent(AddedTaskContract.Intent.OnDescriptionValueChange(description))
            }
            PriorityItem { priority ->
                sendIntent(AddedTaskContract.Intent.OnPriorityValueChange(priority))
            }
            DeadlineDateItem { localDate ->
                sendIntent(AddedTaskContract.Intent.OnDeadlineDateValueChange(localDate))
            }
        }
    }
}

@Composable
@PreviewPhone
private fun AddTaskScreenPreview() {
    TodoTheme {
        AddTaskScreen {}
    }
}