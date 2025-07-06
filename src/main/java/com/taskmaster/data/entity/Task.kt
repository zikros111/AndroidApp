package com.taskmaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val priority: Int = 5,
    val sphereId: Long = 1,
    val isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val dueDate: Date? = null,
    val completedAt: Date? = null,
    val xpReward: Int = 0,
    val parentTaskId: Long? = null
)
