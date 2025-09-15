package com.example.todo.data.db.type_convertors

import androidx.room.TypeConverter
import com.example.todo.domain.model.BusinessPriority
import com.example.todo.domain.model.StorageStatus

class TaskStatusConvertor {
    @TypeConverter
    fun fromStatus(status: StorageStatus): String = status.name.lowercase()

    @TypeConverter
    fun toStatus(value: String): StorageStatus {
        return StorageStatus.valueOf(value.uppercase())
    }
}

class TaskPriorityConvertor {
    @TypeConverter
    fun fromPriority(priority: BusinessPriority): String = priority.name.lowercase()

    @TypeConverter
    fun toPriority(value: String): BusinessPriority =
        BusinessPriority.valueOf(value.uppercase())
}