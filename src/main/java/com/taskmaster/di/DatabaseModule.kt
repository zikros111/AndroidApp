package com.taskmaster.di

import android.content.Context
import androidx.room.Room
import com.taskmaster.data.dao.*
import com.taskmaster.data.database.TaskMasterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTaskMasterDatabase(@ApplicationContext context: Context): TaskMasterDatabase {
        return Room.databaseBuilder(
            context,
            TaskMasterDatabase::class.java,
            "taskmaster_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskMasterDatabase): TaskDao = database.taskDao()

    @Provides
    fun provideUserDao(database: TaskMasterDatabase): UserDao = database.userDao()

    @Provides
    fun provideSphereDao(database: TaskMasterDatabase): SphereDao = database.sphereDao()

    @Provides
    fun provideAchievementDao(database: TaskMasterDatabase): AchievementDao = database.achievementDao()

    @Provides
    fun provideFriendDao(database: TaskMasterDatabase): FriendDao = database.friendDao()

    @Provides
    fun provideReminderDao(database: TaskMasterDatabase): ReminderDao = database.reminderDao()
}
