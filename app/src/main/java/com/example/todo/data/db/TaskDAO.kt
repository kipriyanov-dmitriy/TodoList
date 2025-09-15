package com.example.todo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.domain.model.StorageStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItemEntity): Long

    @Query("SELECT * FROM tasks")
    fun getAllTaskFlow(): Flow<List<TaskItemEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskItemEntity

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE tasks SET storageStatus = :status WHERE id = :id")
    suspend fun updateTaskById(id: Long, status: StorageStatus)
}