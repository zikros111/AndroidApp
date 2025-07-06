package com.taskmaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Date? = null,
    val requirement: Int = 0,
    val currentProgress: Int = 0,
    val type: String, // "tasks", "streak", "xp", "sphere", "consistency", "speed"
    val category: String = "general", // "daily", "weekly", "monthly", "special"
    val rarity: String = "common", // "common", "rare", "epic", "legendary"
    val xpReward: Int = 0,
    val badgeColor: String = "#FFD700",
    val conditions: String = "", // JSON для сложных условий
    val isSecret: Boolean = false,
    val isRepeatable: Boolean = false,
    val seasonId: String? = null
)
