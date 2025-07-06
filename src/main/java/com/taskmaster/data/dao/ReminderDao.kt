package com.taskmaster.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmaster.data.entity.Reminder
import java.util.Date

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE isCompleted = 0 ORDER BY reminderTime ASC")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE DATE(reminderTime) = DATE('now') AND isCompleted = 0")
    fun getTodayReminders(): LiveData<List<Reminder>>

    @Insert
    suspend fun insertReminder(reminder: Reminder): Long

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("UPDATE reminders SET isCompleted = 1 WHERE id = :reminderId")
    suspend fun completeReminder(reminderId: Long)
}
