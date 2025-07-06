package com.taskmaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taskmaster.databinding.ItemCalendarDayBinding
import java.text.SimpleDateFormat
import java.util.*

sealed class CalendarDay {
    data class DateDay(val date: Date) : CalendarDay()
    object EmptyDay : CalendarDay()
}

class CalendarAdapter(
    private val onDateClick: (Date) -> Unit
) : ListAdapter<CalendarDay, CalendarAdapter.CalendarViewHolder>(CalendarDayDiffCallback()) {

    private val dateFormat = SimpleDateFormat("d", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendarDay = getItem(position)
        holder.bind(calendarDay)
    }

    inner class CalendarViewHolder(private val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarDay: CalendarDay) {
            when (calendarDay) {
                is CalendarDay.DateDay -> {
                    binding.textDay.text = dateFormat.format(calendarDay.date)
                    binding.root.setOnClickListener { onDateClick(calendarDay.date) }
                    binding.root.alpha = 1.0f
                }
                is CalendarDay.EmptyDay -> {
                    binding.textDay.text = ""
                    binding.root.setOnClickListener(null)
                    binding.root.alpha = 0.3f
                }
            }
        }
    }
}

class CalendarDayDiffCallback : DiffUtil.ItemCallback<CalendarDay>() {
    override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
        return when {
            oldItem is CalendarDay.EmptyDay && newItem is CalendarDay.EmptyDay -> true
            oldItem is CalendarDay.DateDay && newItem is CalendarDay.DateDay ->
                oldItem.date.time == newItem.date.time
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
        return oldItem == newItem
    }
}
