package com.taskmaster.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taskmaster.R
import com.taskmaster.data.entity.Task
import com.taskmaster.databinding.ItemTaskBinding
import com.taskmaster.utils.PriorityUtils

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onCompleteClick: (Task) -> Unit,
    private val onPostponeClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                textTitle.text = task.title
                textDescription.text = task.description
                textPriority.text = "Приоритет: ${task.priority}"
                textXp.text = "+${task.xpReward} XP"

                // Set priority color
                val priorityColor = PriorityUtils.getPriorityColor(task.priority)
                priorityIndicator.setColorFilter(
                    ContextCompat.getColor(root.context, priorityColor)
                )

                // Set click listeners
                root.setOnClickListener { onTaskClick(task) }
                buttonComplete.setOnClickListener { onCompleteClick(task) }
                buttonPostpone.setOnClickListener { onPostponeClick(task) }
                buttonDelete.setOnClickListener { onDeleteClick(task) }
            }
        }
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
