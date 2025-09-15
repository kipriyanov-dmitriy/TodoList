package com.example.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.data.db.type_convertors.TaskPriorityConvertor
import com.example.todo.data.db.type_convertors.TaskStatusConvertor

@Database(entities = [TaskItemEntity::class], version = 1)
@TypeConverters(TaskStatusConvertor::class, TaskPriorityConvertor::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDAO
}