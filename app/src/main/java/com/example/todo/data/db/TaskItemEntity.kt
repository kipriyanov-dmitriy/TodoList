package com.example.todo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "backlog_tasks")
data class TaskItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val dateOfCreate: Long,
    val priority: Int,
    val parentId: Long? = null,
    val storageStatus: Int,
)