package com.taskmaster.data.repository

import androidx.lifecycle.LiveData
import com.taskmaster.data.dao.TaskDao
import com.taskmaster.data.entity.Task
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()
    
    fun getTodayTasks(): LiveData<List<Task>> = taskDao.getTodayTasks()
    
    fun getTasksByDate(date: Date): LiveData<List<Task>> = taskDao.getTasksByDate(date)
    
    fun getSubTasks(parentId: Long): LiveData<List<Task>> = taskDao.getSubTasks(parentId)
    
    fun getTasksBySphere(sphereId: Long): LiveData<List<Task>> = taskDao.getTasksBySphere(sphereId)
    
    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)
    
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    
    suspend fun completeTask(taskId: Long, completedAt: Date) = taskDao.completeTask(taskId, completedAt)
    
    suspend fun postponeTask(taskId: Long, newDate: Date) = taskDao.postponeTask(taskId, newDate)
    
    suspend fun getCompletedTasksCount(date: Date): Int = taskDao.getCompletedTasksCount(date)
    
    suspend fun getTotalTasksCount(date: Date): Int = taskDao.getTotalTasksCount(date)
}
