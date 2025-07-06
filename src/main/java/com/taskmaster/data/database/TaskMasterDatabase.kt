package com.taskmaster.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.taskmaster.data.dao.*
import com.taskmaster.data.entity.*
import com.taskmaster.utils.DateConverter

@Database(
    entities = [
        Task::class,
        User::class,
        Sphere::class,
        Achievement::class,
        Friend::class,
        Reminder::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TaskMasterDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
    abstract fun sphereDao(): SphereDao
    abstract fun achievementDao(): AchievementDao
    abstract fun friendDao(): FriendDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: TaskMasterDatabase? = null

        fun getDatabase(context: Context): TaskMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskMasterDatabase::class.java,
                    "taskmaster_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
