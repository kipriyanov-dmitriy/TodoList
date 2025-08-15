package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.todo_list_screen.InProgressScreen

@Composable
fun InProgressNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = InProgressRoute.InProgress
    ) {
        composable<InProgressRoute.InProgress> {
            InProgressScreen()
        }
    }
}