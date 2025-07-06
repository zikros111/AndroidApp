package com.taskmaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "challenges")
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val creatorId: Long,
    val type: String, // "duel", "group", "global"
    val status: String, // "pending", "active", "completed", "cancelled"
    val startDate: Date,
    val endDate: Date,
    val rules: String, // JSON с правилами
    val maxParticipants: Int = 0,
    val currentParticipants: Int = 0,
    val reward: String = "",
    val xpReward: Int = 0,
    val isPublic: Boolean = true,
    val category: String = "",
    val difficulty: Int = 1, // 1-5
    val createdAt: Date = Date(),
    val joinCode: String? = null
)

@Entity(tableName = "challenge_participants")
data class ChallengeParticipant(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val challengeId: Long,
    val userId: Long,
    val joinedAt: Date = Date(),
    val score: Int = 0,
    val position: Int = 0,
    val isCompleted: Boolean = false,
    val completedAt: Date? = null
)
