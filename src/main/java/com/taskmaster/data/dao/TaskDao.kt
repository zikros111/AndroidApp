package com.taskmaster.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmaster.data.entity.Task
import java.util.Date

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE parentTaskId IS NULL ORDER BY createdAt DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE DATE(dueDate/1000, 'unixepoch') = DATE('now') AND isCompleted = 0")
    fun getTodayTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE DATE(dueDate/1000, 'unixepoch') = DATE(:date/1000, 'unixepoch')")
    fun getTasksByDate(date: Date): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE parentTaskId = :parentId")
    fun getSubTasks(parentId: Long): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE sphereId = :sphereId")
    fun getTasksBySphere(sphereId: Long): LiveData<List<Task>>

    @Insert
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET isCompleted = 1, completedAt = :completedAt WHERE id = :taskId")
    suspend fun completeTask(taskId: Long, completedAt: Date)

    @Query("UPDATE tasks SET dueDate = :newDate WHERE id = :taskId")
    suspend fun postponeTask(taskId: Long, newDate: Date)

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1 AND DATE(completedAt/1000, 'unixepoch') = DATE('now')")
    suspend fun getTodayCompletedTasksCount(): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE DATE(dueDate/1000, 'unixepoch') = DATE('now')")
    suspend fun getTodayTotalTasksCount(): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1 AND DATE(completedAt/1000, 'unixepoch') = DATE(:date/1000, 'unixepoch')")
    suspend fun getCompletedTasksCount(date: Date): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE DATE(dueDate/1000, 'unixepoch') = DATE(:date/1000, 'unixepoch')")
    suspend fun getTotalTasksCount(date: Date): Int
}
