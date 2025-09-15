package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.todo.core.PreviewPhone
import com.example.todo.ui.components.TodoTopAppBar
import com.example.todo.ui.contract.BacklogContract
import com.example.todo.ui.model.TaskUiModel
import com.example.todo.ui.model.BottomTab
import com.example.todo.ui.model.bottomTabs
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.todo_list_screens.viewmodels.TodoListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodoListScreen(
    selectedTab: BottomTab,
    onAddNoteClick: () -> Unit,
) {
    val viewModel: TodoListViewModel = koinViewModel()
    val viewState by viewModel.viewState.collectAsState()
    val sendIntent = remember(viewModel) { viewModel::handleIntent }
    TodoListContent(
        viewState = viewState,
        selectedTab = selectedTab,
        onAddNoteClick = onAddNoteClick,
        sendIntent = sendIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoListContent(
    viewState: TaskUiModel,
    selectedTab: BottomTab = BottomTab.InProgress,
    onAddNoteClick: () -> Unit,
    sendIntent: (BacklogContract.Intent) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(selectedTab) }

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
                BottomTab.InProgress -> InProgressContent(
                    state = viewState,
                    sendIntent = sendIntent
                )
                BottomTab.Backlog -> BacklogContent(
                    state = viewState,
                    onAddNoteClick = onAddNoteClick,
                    sendIntent = sendIntent
                )
                BottomTab.Archive -> ArchiveScreen()
            }
        }
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
//        TodoListContent(onAddNoteClick = {})
    }
}