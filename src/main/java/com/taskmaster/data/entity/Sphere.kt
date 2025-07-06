package com.taskmaster.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spheres")
data class Sphere(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val color: String,
    val icon: String,
    val xp: Int = 0,
    val level: Int = 1,
    val order: Int = 0
)
