package com.taskmaster.utils

import com.taskmaster.R

object PriorityUtils {
    fun getPriorityColor(priority: Int): Int {
        return when {
            priority <= 3 -> R.color.priority_low
            priority <= 6 -> R.color.priority_medium
            else -> R.color.priority_high
        }
    }

    fun getPriorityText(priority: Int): String {
        return when {
            priority <= 3 -> "Низкий"
            priority <= 6 -> "Средний"
            else -> "Высокий"
        }
    }
}
