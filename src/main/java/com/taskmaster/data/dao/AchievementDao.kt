package com.taskmaster.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmaster.data.entity.Achievement
import java.util.Date

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements ORDER BY isUnlocked DESC, id ASC")
    fun getAllAchievements(): LiveData<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE isUnlocked = 1 ORDER BY unlockedAt DESC")
    fun getUnlockedAchievements(): LiveData<List<Achievement>>

    @Insert
    suspend fun insertAchievement(achievement: Achievement): Long

    @Update
    suspend fun updateAchievement(achievement: Achievement)

    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id = :achievementId")
    suspend fun unlockAchievement(achievementId: Long, unlockedAt: Date)

    @Query("UPDATE achievements SET currentProgress = :progress WHERE id = :achievementId")
    suspend fun updateProgress(achievementId: Long, progress: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<Achievement>)
}
