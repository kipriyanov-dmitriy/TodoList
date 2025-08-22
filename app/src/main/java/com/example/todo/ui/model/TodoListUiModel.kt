package com.example.todo.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.example.todo.R
import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.model.BusinessPriority
import com.example.todo.domain.model.StorageStatus
import com.example.todo.ui.components.Priority
import kotlinx.serialization.Serializable
import java.time.LocalDate

const val EMPTY_STRING = ""

//навигация
@Serializable
enum class BottomTabType {
    IN_PROGRESS,
    BACKLOG,
    ARCHIVE
}

@Serializable
sealed class BottomTab(@StringRes val titleId: Int, @DrawableRes val iconId: Int) {
    data object InProgress : BottomTab(R.string.in_work, R.drawable.ic_in_work_36)
    data object Backlog : BottomTab(R.string.backlog, R.drawable.ic_backlog_36)
    data object Archive : BottomTab(R.string.archive, R.drawable.ic_archive_36)

    companion object {
        fun fromType(type: BottomTabType): BottomTab = when (type) {
            BottomTabType.IN_PROGRESS -> InProgress
            BottomTabType.BACKLOG -> Backlog
            BottomTabType.ARCHIVE -> Archive
        }

        fun toType(tab: BottomTab): BottomTabType = when (tab) {
            InProgress -> BottomTabType.IN_PROGRESS
            Backlog -> BottomTabType.BACKLOG
            Archive -> BottomTabType.ARCHIVE
        }
    }
}

val bottomTabs = listOf(
    BottomTab.InProgress,
    BottomTab.Backlog,
    BottomTab.Archive
)

//ui стейты
data class AddedTaskUiModel(
    val title: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val dateOfCreate: LocalDate = LocalDate.now(),
    val priority: Priority = Priority.Low,
)

@Stable
data class TaskUiModel(
    val listOfTasks: List<TaskItemUiModel>,
)

data class TaskItemUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val dateOfCreate: String,
    val priority: Priority,
    val storageStatus: StorageStatus
)

//маперы
fun TaskItemUiModel.toTaskItem() =
    TaskItem(
        id = id,
        title = title,
        description = description,
        dateOfCreate = LocalDate.now(),
        businessPriority = BusinessPriority.entries[priority.ordinal],
        storageStatus = storageStatus
    )

fun AddedTaskUiModel.toTaskItem() =
    TaskItem(
        title = title,
        description = description,
        dateOfCreate = dateOfCreate,
        businessPriority = BusinessPriority.entries[priority.ordinal]
    )