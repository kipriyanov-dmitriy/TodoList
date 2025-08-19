package com.example.todo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.todo_list_screens.AddNoteScreen
import com.example.todo.ui.todo_list_screens.TodoListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TodoRoute.TodoList
    ) {
        composable<TodoRoute.TodoList> {
            TodoListScreen(
                onAddNoteClick = { navController.navigate(TodoRoute.AddNote) }
            )
        }
        composable<TodoRoute.AddNote> {
            AddNoteScreen(
                onNoteAdded = { navController.popBackStack() }
            )
        }
    }
}


