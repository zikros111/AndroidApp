package com.taskmaster.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmaster.data.entity.Task
import com.taskmaster.data.repository.TaskRepository
import com.taskmaster.data.repository.UserRepository
import com.taskmaster.utils.XpCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date> = _selectedDate

    private val _todayProgress = MutableLiveData<Float>()
    val todayProgress: LiveData<Float> = _todayProgress

    private val _todayXp = MutableLiveData<Int>()
    val todayXp: LiveData<Int> = _todayXp

    private val _currentStreak = MutableLiveData<Int>()
    val currentStreak: LiveData<Int> = _currentStreak

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    val todayTasks = taskRepository.getTodayTasks()
    val allTasks = taskRepository.getAllTasks()

    init {
        loadTodayData()
    }

    fun getTasksByDate(date: Date): LiveData<List<Task>> = taskRepository.getTasksByDate(date)

    fun selectDate(date: Date) {
        _selectedDate.value = date
    }

    fun createTask(
        title: String,
        description: String = "",
        priority: Int = 5,
        sphereId: Long = 1,
        dueDate: Date = Date()
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val xp = XpCalculator.calculateXp(priority)
                val task = Task(
                    title = title,
                    description = description,
                    priority = priority,
                    sphereId = sphereId,
                    dueDate = dueDate,
                    xpReward = xp
                )
                taskRepository.insertTask(task)
                loadTodayData()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка создания задачи: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            try {
                val now = Date()
                taskRepository.completeTask(task.id, now)
                userRepository.addXp(task.xpReward)
                loadTodayData()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка выполнения задачи: ${e.message}"
            }
        }
    }

    fun postponeTask(task: Task, newDate: Date? = null) {
        viewModelScope.launch {
            try {
                val targetDate = newDate ?: Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
                taskRepository.postponeTask(task.id, targetDate)
                loadTodayData()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка переноса задачи: ${e.message}"
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
                loadTodayData()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка удаления задачи: ${e.message}"
            }
        }
    }

    private fun loadTodayData() {
        viewModelScope.launch {
            try {
                val completed = taskRepository.getTodayCompletedTasksCount()
                val total = taskRepository.getTodayTotalTasksCount()
                val progress = if (total > 0) completed.toFloat() / total.toFloat() else 0f
                _todayProgress.value = progress

                // Простая логика для XP - можно улучшить позже
                _todayXp.value = completed * 25

                // Простая логика для streak - можно улучшить позже
                _currentStreak.value = 1

            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки данных: ${e.message}"
            }
        }
    }

    suspend fun getDayProgress(date: Date): Float {
        return try {
            val completed = taskRepository.getCompletedTasksCount(date)
            val total = taskRepository.getTotalTasksCount(date)
            if (total > 0) completed.toFloat() / total.toFloat() else 0f
        } catch (e: Exception) {
            0f
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
