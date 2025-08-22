package com.example.todo.ui.navigation

import com.example.todo.ui.model.BottomTabType
import kotlinx.serialization.Serializable

sealed interface TodoRoute {
    @Serializable
    data class TodoList(val selectedTab: BottomTabType = BottomTabType.IN_PROGRESS) : TodoRoute

    @Serializable
    data object AddNote : TodoRoute
}