package com.example.todo.ui.todo_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo.core.PreviewPhone
import com.example.todo.ui.components.TodoTopAppBar
import com.example.todo.ui.model.BottomTab
import com.example.todo.ui.model.bottomTabs
import com.example.todo.ui.navigation.ArchiveNavHost
import com.example.todo.ui.navigation.BacklogNavHost
import com.example.todo.ui.navigation.InProgressNavHost
import com.example.todo.ui.theme.TodoTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodoListScreen(

) {
    val viewModel: TodoListViewModel = koinViewModel()

    TodoListContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoListContent(

) {
    var selectedTab by remember { mutableStateOf<BottomTab>(BottomTab.InProgress) }

    Scaffold(
        topBar = {
            TodoTopAppBar()
        },
        bottomBar = {
            NavigationBar {
                bottomTabs.forEach { tab ->
                    NavigationBarItem(
                        selected = tab == selectedTab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(
                            painter = painterResource(tab.iconId),
                            contentDescription = stringResource(tab.titleId)
                        ) },
                        label = { Text(text = stringResource(tab.titleId)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            when (selectedTab) {
                BottomTab.InProgress -> InProgressNavHost()
                BottomTab.Backlog -> BacklogNavHost()
                BottomTab.Archive -> ArchiveNavHost()
            }
        }
    }
}

@Composable
fun InProgressScreen() {
    Box(Modifier.fillMaxSize()) {
        Text("Экран В работе", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun BacklogScreen(onAddNoteClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Text("Список бэклога", modifier = Modifier.align(Alignment.Center))

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
fun AddNoteScreen(categoryId: Int?, onNoteAdded: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Text("Добавить заметку в категорию: $categoryId", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ArchiveScreen() {
    Box(Modifier.fillMaxSize()) {
        Text("Экран Архив", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
@PreviewPhone
private fun TodoListPreview() {
    TodoTheme {
        TodoListContent()
    }
}