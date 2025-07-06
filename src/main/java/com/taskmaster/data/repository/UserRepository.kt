package com.taskmaster.data.repository

import androidx.lifecycle.LiveData
import com.taskmaster.data.dao.UserDao
import com.taskmaster.data.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getCurrentUser(): LiveData<User> = userDao.getCurrentUser()
    
    suspend fun getUserByUsername(username: String): User? = userDao.getUserByUsername(username)
    
    suspend fun getUserById(userId: Long): User? = userDao.getUserById(userId)
    
    suspend fun insertUser(user: User): Long = userDao.insertUser(user)
    
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    suspend fun addXp(xp: Int) = userDao.addXp(xp)
    
    suspend fun updateLevel(level: Int) = userDao.updateLevel(level)
    
    suspend fun updateStreak(streak: Int, maxStreak: Int) = userDao.updateStreak(streak, maxStreak)
    
    suspend fun searchUsers(query: String): List<User> = userDao.searchUsers(query)
}
