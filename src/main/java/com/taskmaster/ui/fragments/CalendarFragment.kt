package com.taskmaster.ui.fragments

import android.os.Bundle
import com.taskmaster.ui.adapter.CalendarDay
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.taskmaster.databinding.FragmentCalendarBinding
import com.taskmaster.ui.adapter.CalendarAdapter
import com.taskmaster.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendar()
        setupObservers()
    }

    private fun setupCalendar() {
        calendarAdapter = CalendarAdapter { date ->
            taskViewModel.selectDate(date)
            showTasksForDate(date)
        }

        binding.recyclerViewCalendar.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(context, 7)
        }

        generateCalendarDays()
    }

    private fun setupObservers() {
        taskViewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            date?.let { showTasksForDate(it) }
        }
    }

    private fun generateCalendarDays() {
        val calendar = Calendar.getInstance()
        val today = calendar.time
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        val days = mutableListOf<CalendarDay>()

        // Add empty days for the first week
        for (i in 0 until firstDayOfWeek) {
            days.add(CalendarDay.EmptyDay)
        }

        // Add days of the month
        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            days.add(CalendarDay.DateDay(Date(calendar.timeInMillis)))
        }

        calendarAdapter.submitList(days)
    }

    private fun showTasksForDate(date: Date) {
        lifecycleScope.launch {
            val progress = taskViewModel.getDayProgress(date)
            // Update UI with progress for selected date
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
