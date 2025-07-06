package com.taskmaster.utils

object XpCalculator {
    private const val BASE_XP = 10
    private const val PRIORITY_MULTIPLIER = 5

    fun calculateXp(priority: Int): Int {
        return BASE_XP + (priority * PRIORITY_MULTIPLIER)
    }

    fun calculateLevelFromXp(xp: Int): Int {
        return (xp / 100) + 1
    }

    fun getXpForNextLevel(currentLevel: Int): Int {
        return currentLevel * 100
    }
}
