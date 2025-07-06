package com.taskmaster.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.taskmaster.databinding.FragmentTodayBinding
import com.taskmaster.ui.adapter.TaskAdapter
import com.taskmaster.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task -> /* Handle task click */ },
            onCompleteClick = { task ->
                taskViewModel.completeTask(task)
                showCompletionFeedback(task)
            },
            onPostponeClick = { task ->
                taskViewModel.postponeTask(task)
            },
            onDeleteClick = { task ->
                showDeleteConfirmation(task)
            }
        )

        binding.recyclerViewTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        taskViewModel.todayTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
            binding.emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
            updateTasksInfo(tasks)
        }

        taskViewModel.todayProgress.observe(viewLifecycleOwner) { progress ->
            binding.progressBar.progress = (progress * 100).toInt()
            binding.textProgress.text = "${(progress * 100).toInt()}%"
        }

        taskViewModel.todayXp.observe(viewLifecycleOwner) { xp ->
            binding.textXpToday.text = xp.toString()
        }

        taskViewModel.currentStreak.observe(viewLifecycleOwner) { streak ->
            binding.textStreak.text = streak.toString()
        }

        taskViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                taskViewModel.clearError()
            }
        }
    }

    private fun setupClickListeners() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Новая задача")

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val titleInput = EditText(context)
        titleInput.hint = "Название задачи"
        layout.addView(titleInput)

        val descriptionInput = EditText(context)
        descriptionInput.hint = "Описание (необязательно)"
        layout.addView(descriptionInput)

        builder.setView(layout)

        builder.setPositiveButton("Создать") { _, _ ->
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            if (title.isNotEmpty()) {
                taskViewModel.createTask(
                    title = title,
                    description = description,
                    priority = 5,
                    sphereId = 1
                )
            }
        }

        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateTasksInfo(tasks: List<com.taskmaster.data.entity.Task>) {
        val completed = tasks.count { it.isCompleted }
        val total = tasks.size
        binding.textTasksInfo.text = if (total > 0) {
            "$completed из $total задач выполнено"
        } else {
            "Нет задач на сегодня"
        }
    }

    private fun showCompletionFeedback(task: com.taskmaster.data.entity.Task) {
        val message = "🎉 Задача выполнена! +${task.xpReward} XP"
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showDeleteConfirmation(task: com.taskmaster.data.entity.Task) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить задачу?")
            .setMessage("Это действие нельзя отменить")
            .setPositiveButton("Удалить") { _, _ ->
                taskViewModel.deleteTask(task)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
