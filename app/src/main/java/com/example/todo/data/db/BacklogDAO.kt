package com.example.todo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BacklogTasksDAO {
    companion object {
        private const val BACKLOG_STORAGE_STATUS = 1
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItemEntity): Long

    @Query("SELECT * FROM backlog_tasks WHERE storageStatus = $BACKLOG_STORAGE_STATUS")
    suspend fun getAllBacklogTask(): List<TaskItemEntity>

    @Query("SELECT * FROM backlog_tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskItemEntity

    @Query("DELETE FROM backlog_tasks WHERE id = :id")
    suspend fun deleteById(id: Long)
}