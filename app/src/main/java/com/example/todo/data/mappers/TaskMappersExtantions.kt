package com.example.todo.data.mappers

import com.example.todo.data.db.TaskItemEntity
import com.example.todo.domain.model.TaskItem
import com.example.todo.domain.model.BusinessPriority
import com.example.todo.domain.model.StorageStatus
import java.time.Instant
import java.time.ZoneId

fun TaskItemEntity.toTaskItem(): TaskItem {
    val date = Instant.ofEpochMilli(this.dateOfCreate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return TaskItem(
        id = id,
        title = title,
        description = description,
        dateOfCreate = date,
        businessPriority = priority,
        storageStatus = storageStatus
    )
}

fun TaskItem.toTaskItemEntity(): TaskItemEntity {
    val date = this.dateOfCreate.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
    return TaskItemEntity(
        title = title,
        description = description,
        dateOfCreate = date,
        priority = businessPriority,
        storageStatus = storageStatus
    )
}