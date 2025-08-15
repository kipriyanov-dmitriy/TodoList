package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.todo_list_screen.ArchiveScreen

@Composable
fun ArchiveNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ArchiveRoute.Archive
    ) {
        composable<ArchiveRoute.Archive> {
            ArchiveScreen()
        }
    }
}