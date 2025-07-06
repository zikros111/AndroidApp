package com.taskmaster.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.taskmaster.data.entity.Task
import java.util.*

fun showAddTaskDialog(context: Context, onTaskCreated: (Task) -> Unit) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Новая задача")
    
    val input = EditText(context)
    input.hint = "Введите название задачи"
    builder.setView(input)
    
    builder.setPositiveButton("Создать") { _, _ ->
        val title = input.text.toString()
        if (title.isNotEmpty()) {
            val task = Task(
                title = title,
                sphereId = 1, // Default sphere
                dueDate = Date()
            )
            onTaskCreated(task)
        }
    }
    
    builder.setNegativeButton("Отмена") { dialog, _ ->
        dialog.cancel()
    }
    
    builder.show()
}

fun showDatePickerDialog(context: Context, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
