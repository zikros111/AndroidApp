package com.taskmaster.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmaster.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): LiveData<User>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET totalXp = totalXp + :xp WHERE isCurrentUser = 1")
    suspend fun addXp(xp: Int)

    @Query("UPDATE users SET level = :level WHERE isCurrentUser = 1")
    suspend fun updateLevel(level: Int)

    @Query("UPDATE users SET currentStreak = :streak, maxStreak = :maxStreak WHERE isCurrentUser = 1")
    suspend fun updateStreak(streak: Int, maxStreak: Int)

    @Query("SELECT * FROM users WHERE username LIKE '%' || :query || '%' AND isCurrentUser = 0")
    suspend fun searchUsers(query: String): List<User>
}
