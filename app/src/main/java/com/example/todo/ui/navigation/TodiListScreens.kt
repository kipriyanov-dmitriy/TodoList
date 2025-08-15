package com.example.todo.ui.navigation

import kotlinx.serialization.Serializable

sealed interface InProgressRoute {
    @Serializable
    data object InProgress : InProgressRoute
}

sealed interface BacklogRoute {
    @Serializable
    data object Backlog : BacklogRoute

    @Serializable
    data class AddNote(val categoryId: Int? = null) : BacklogRoute
}

sealed interface ArchiveRoute {
    @Serializable
    data object Archive : ArchiveRoute
}