package com.example.todo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.domain.model.BusinessPriority
import com.example.todo.domain.model.StorageStatus

@Entity(tableName = "tasks")
data class TaskItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val dateOfCreate: Long,
    val priority: BusinessPriority,
    val parentId: Long? = null,
    val storageStatus: StorageStatus,
)