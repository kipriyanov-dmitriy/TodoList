package com.example.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskItemEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun backlogTasksDao(): BacklogTasksDAO
}