package com.taskmaster.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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

        try {
            // Insert default user first
            val defaultUser = com.taskmaster.data.entity.User(
                username = "taskmaster_user",
                name = "Игрок TaskMaster",
                city = "Москва",
                totalXp = 0,
                level = 1,
                currentStreak = 0,
                maxStreak = 0,
                isCurrentUser = true,
                createdAt = Date()
            )
            val userId = database.userDao().insertUser(defaultUser)

            // Insert default spheres
            val spheres = listOf(
                com.taskmaster.data.entity.Sphere(name = "🏋️ Спорт", color = "#4CAF50", icon = "fitness", order = 0),
                com.taskmaster.data.entity.Sphere(name = "📚 Обучение", color = "#2196F3", icon = "education", order = 1),
                com.taskmaster.data.entity.Sphere(name = "💼 Работа", color = "#9C27B0", icon = "work", order = 2),
                com.taskmaster.data.entity.Sphere(name = "🏠 Быт", color = "#FF9800", icon = "home", order = 3),
                com.taskmaster.data.entity.Sphere(name = "❤️ Семья", color = "#E91E63", icon = "family", order = 4)
            )
            database.sphereDao().insertSpheres(spheres)

            // Insert default achievements
            val achievements = listOf(
                com.taskmaster.data.entity.Achievement(
                    title = "🚀 Первые шаги",
                    description = "Выполните первую задачу",
                    icon = "🚀",
                    type = "tasks",
                    requirement = 1,
                    currentProgress = 0
                ),
                com.taskmaster.data.entity.Achievement(
                    title = "💪 Продуктивность",
                    description = "Выполните 10 задач",
                    icon = "💪",
                    type = "tasks",
                    requirement = 10,
                    currentProgress = 0
                ),
                com.taskmaster.data.entity.Achievement(
                    title = "🔥 Недельный streak",
                    description = "Выполняйте задачи 7 дней подряд",
                    icon = "🔥",
                    type = "streak",
                    requirement = 7,
                    currentProgress = 0
                ),
                com.taskmaster.data.entity.Achievement(
                    title = "⭐ Мастер уровня",
                    description = "Достигните 500 XP",
                    icon = "⭐",
                    type = "xp",
                    requirement = 500,
                    currentProgress = 0
                )
            )
            database.achievementDao().insertAchievements(achievements)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
