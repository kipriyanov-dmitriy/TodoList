package com.example.todo.ui.todo_list_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.core.PreviewPhone
import com.example.todo.ui.theme.TODOTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodoListScreen(

) {
    val viewModel: TodoListViewModel = koinViewModel()

    Scaffold(

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}

@Composable
@PreviewPhone
private fun TodoListPreview() {
    TODOTheme {
        TodoListScreen()
    }
}