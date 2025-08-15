package com.example.todo.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import com.example.todo.R

sealed class BottomTab(@StringRes val titleId: Int, @DrawableRes val iconId: Int) {
    data object InProgress : BottomTab(R.string.in_work, R.drawable.ic_in_work_36)
    data object Backlog : BottomTab(R.string.backlog, R.drawable.ic_backlog_36)
    data object Archive : BottomTab(R.string.archive, R.drawable.ic_archive_36)
}

val bottomTabs = listOf(
    BottomTab.InProgress,
    BottomTab.Backlog,
    BottomTab.Archive
)

data class TodoListUiModel(
    val listOfTask: List<String> = listOf()
)