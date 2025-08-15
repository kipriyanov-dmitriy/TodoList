package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todo.ui.todo_list_screen.AddNoteScreen
import com.example.todo.ui.todo_list_screen.BacklogScreen

@Composable
fun BacklogNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BacklogRoute.Backlog
    ) {
        composable<BacklogRoute.Backlog> {
            BacklogScreen(
                onAddNoteClick = {
                    navController.navigate(BacklogRoute.AddNote(categoryId = 5))
                }
            )
        }
        composable<BacklogRoute.AddNote> { entry ->
            val args = entry.toRoute<BacklogRoute.AddNote>()
            AddNoteScreen(
                categoryId = args.categoryId,
                onNoteAdded = { navController.popBackStack() }
            )
        }
    }
}