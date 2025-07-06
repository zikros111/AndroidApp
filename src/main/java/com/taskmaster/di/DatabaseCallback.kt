package com.taskmaster.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taskmaster.data.entity.Achievement
import com.taskmaster.data.entity.Sphere
import com.taskmaster.data.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        val database = TaskMasterDatabase.getDatabase(context)
        
        // Insert default spheres
        val spheres = listOf(
            Sphere(name = "Работа", color = "#2196F3", icon = "work", order = 0),
            Sphere(name = "Здоровье", color = "#4CAF50", icon = "health", order = 1),
            Sphere(name = "Образование", color = "#9C27B0", icon = "education", order = 2),
            Sphere(name = "Семья", color = "#E91E63", icon = "family", order = 3),
            Sphere(name = "Хобби", color = "#FF9800", icon = "hobby", order = 4)
        )
        database.sphereDao().insertSpheres(spheres)

        // Insert default achievements
        val achievements = listOf(
            Achievement(
                title = "Первый шаг",
                description = "Выполните первую задачу",
                icon = "first_task",
                type = "tasks",
                requirement = 1
            ),
            Achievement(
                title = "Продуктивная неделя",
                description = "Выполните 10 задач",
                icon = "productive_week",
                type = "tasks",
                requirement = 10
            ),
            Achievement(
                title = "Мастер привычек",
                description = "Поддерживайте streak 7 дней",
                icon = "habit_master",
                type = "streak",
                requirement = 7
            )
        )
        database.achievementDao().insertAchievements(achievements)

        // Insert default user
        val defaultUser = User(
            username = "user",
            name = "Пользователь",
            city = "Город",
            isCurrentUser = true,
            createdAt = Date()
        )
        database.userDao().insertUser(defaultUser)
    }
}
