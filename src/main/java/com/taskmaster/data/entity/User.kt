package com.taskmaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val name: String,
    val city: String = "",
    val avatarUrl: String = "",
    val totalXp: Int = 0,
    val level: Int = 1,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,
    val lastActiveDate: Date = Date(),
    val createdAt: Date = Date(),
    val isCurrentUser: Boolean = false
)
