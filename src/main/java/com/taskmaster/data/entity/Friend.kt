package com.taskmaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "friends")
data class Friend(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val friendId: Long,
    val status: String = "pending", // "pending", "accepted", "blocked"
    val createdAt: Date = Date()
)
