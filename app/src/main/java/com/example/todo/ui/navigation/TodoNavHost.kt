package com.example.todo.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todo.ui.model.BottomTab
import com.example.todo.ui.model.BottomTabType
import com.example.todo.ui.todo_list_screens.AddTaskScreen
import com.example.todo.ui.todo_list_screens.TodoListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TodoRoute.TodoList()
    ) {
        composable<TodoRoute.TodoList> {backStackEntry ->
            val args = backStackEntry.toRoute<TodoRoute.TodoList>()
            val selectedTab = BottomTab.fromType(args.selectedTab)

            TodoListScreen(
                selectedTab = selectedTab,
                onAddNoteClick = { navController.navigate(TodoRoute.AddNote) }
            )
        }
        composable<TodoRoute.AddNote> {
            AddTaskScreen(
                onBackPressed = {
                    navController.navigate(
                        TodoRoute.TodoList(selectedTab = BottomTabType.BACKLOG)
                    ) {
                        popUpTo<TodoRoute.TodoList> { inclusive = true }
                    }
                }
            )
        }
    }
}


