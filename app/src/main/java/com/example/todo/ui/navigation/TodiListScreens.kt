package com.example.todo.ui.navigation

import kotlinx.serialization.Serializable

sealed interface TodoRoute {
    @Serializable
    data object TodoList : TodoRoute

    @Serializable
    data object AddNote : TodoRoute
}