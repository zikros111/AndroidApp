package com.taskmaster.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.taskmaster.databinding.FragmentStatisticsBinding
import com.taskmaster.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            updateStatistics(tasks)
        }
    }

    private fun updateStatistics(tasks: List<com.taskmaster.data.entity.Task>) {
        val completedTasks = tasks.count { it.isCompleted }
        val totalTasks = tasks.size
        val completionRate = if (totalTasks > 0) (completedTasks * 100) / totalTasks else 0

        binding.apply {
            textCompletedTasks.text = completedTasks.toString()
            textTotalTasks.text = totalTasks.toString()
            textCompletionRate.text = "$completionRate%"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
