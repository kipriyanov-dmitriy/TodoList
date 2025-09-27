package com.example.todo.data.mappers

import com.example.todo.data.db.TaskItemEntity
import com.example.todo.domain.model.TaskItem
import java.time.Instant
import java.time.ZoneId

fun TaskItemEntity.toTaskItem(): TaskItem {
    val date = deadlineDate?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
    return TaskItem(
        id = id,
        title = title,
        description = description,
        deadlineDate = date,
        businessPriority = priority,
        storageStatus = storageStatus
    )
}

fun TaskItem.toTaskItemEntity(): TaskItemEntity {
    val date = this.deadlineDate?.atStartOfDay(ZoneId.systemDefault())
        ?.toInstant()
        ?.toEpochMilli()
    return TaskItemEntity(
        title = title,
        description = description,
        deadlineDate = date,
        priority = businessPriority,
        storageStatus = storageStatus
    )
}