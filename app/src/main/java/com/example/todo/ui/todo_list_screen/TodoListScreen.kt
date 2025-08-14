package com.example.todo.ui.todo_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.core.PreviewPhone
import com.example.todo.ui.components.ListItem
import com.example.todo.ui.theme.TodoTheme
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun TodoListScreen(

) {
    val viewModel: TodoListViewModel = koinViewModel()

    TodoListContent()
}

@Composable
private fun TodoListContent(

){
    val list = remember {
        List(35) { index -> "Hello world #$index" }
    }
    Scaffold(

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(list) { item ->
                ListItem(
                    text = item
                ) {
                    println("Clicked: $item")
                }
            }
        }
    }
}

@Composable
@PreviewPhone
private fun TodoListPreview() {
    TodoTheme {
        TodoListContent()
    }
}