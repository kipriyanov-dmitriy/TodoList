package com.example.todo.domain.model

import java.time.LocalDate

data class TaskItem(
    val id: Long = 0,
    val title: String,
    val description: String,
    val deadlineDate: LocalDate?,
    val businessPriority: BusinessPriority,
    val storageStatus: StorageStatus = StorageStatus.BACKLOG,
)

enum class BusinessPriority {
    LOW, MEDIUM, HIGH,
}

enum class StorageStatus {
    IN_PROGRESS, BACKLOG, ARCHIVE,
}