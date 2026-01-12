package com.progressly.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.progressly.app.data.database.entities.TaskEntity
import com.progressly.app.databinding.ItemTaskBinding

class TaskAdapter(
    private val onTaskCompleted: (TaskEntity) -> Unit,
    private val onTaskDeleted: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskEntity) {
            binding.tvTaskTitle.text = task.title
            binding.cbTaskDone.isChecked = task.isCompleted

            binding.cbTaskDone.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onTaskCompleted(task)
                }
            }

            binding.btnDeleteTask.setOnClickListener {
                onTaskDeleted(task)
            }
        }
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity) = oldItem == newItem
    }
}

class WaterLogAdapter(
    private val onWaterDeleted: (com.progressly.app.data.database.entities.WaterLogEntity) -> Unit
) : ListAdapter<com.progressly.app.data.database.entities.WaterLogEntity, WaterLogAdapter.WaterLogViewHolder>(WaterLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterLogViewHolder {
        val binding = com.progressly.app.databinding.ItemWaterLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WaterLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaterLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WaterLogViewHolder(private val binding: com.progressly.app.databinding.ItemWaterLogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(log: com.progressly.app.data.database.entities.WaterLogEntity) {
            binding.tvAmount.text = "${log.amount} ml"
            binding.tvTime.text = log.time
            binding.btnDelete.setOnClickListener {
                onWaterDeleted(log)
            }
        }
    }

    private class WaterLogDiffCallback : DiffUtil.ItemCallback<com.progressly.app.data.database.entities.WaterLogEntity>() {
        override fun areItemsTheSame(oldItem: com.progressly.app.data.database.entities.WaterLogEntity, newItem: com.progressly.app.data.database.entities.WaterLogEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: com.progressly.app.data.database.entities.WaterLogEntity, newItem: com.progressly.app.data.database.entities.WaterLogEntity) = oldItem == newItem
    }
}

class MealAdapter(
    private val onMealDeleted: (com.progressly.app.data.database.entities.MealEntity) -> Unit
) : ListAdapter<com.progressly.app.data.database.entities.MealEntity, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = com.progressly.app.databinding.ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MealViewHolder(private val binding: com.progressly.app.databinding.ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: com.progressly.app.data.database.entities.MealEntity) {
            binding.tvMealName.text = meal.name
            binding.tvCalories.text = "${meal.calories} kcal"
            binding.tvMealType.text = meal.mealType
            binding.tvTime.text = meal.time
            binding.btnDelete.setOnClickListener {
                onMealDeleted(meal)
            }
        }
    }

    private class MealDiffCallback : DiffUtil.ItemCallback<com.progressly.app.data.database.entities.MealEntity>() {
        override fun areItemsTheSame(oldItem: com.progressly.app.data.database.entities.MealEntity, newItem: com.progressly.app.data.database.entities.MealEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: com.progressly.app.data.database.entities.MealEntity, newItem: com.progressly.app.data.database.entities.MealEntity) = oldItem == newItem
    }
}

class HabitAdapter(
    private val onHabitCompleted: (com.progressly.app.data.database.entities.HabitEntity) -> Unit,
    private val onHabitDeleted: (com.progressly.app.data.database.entities.HabitEntity) -> Unit
) : ListAdapter<com.progressly.app.data.database.entities.HabitEntity, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = com.progressly.app.databinding.ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HabitViewHolder(private val binding: com.progressly.app.databinding.ItemHabitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: com.progressly.app.data.database.entities.HabitEntity) {
            binding.tvHabitName.text = habit.name
            binding.tvFrequency.text = habit.frequency
            binding.btnCheckHabit.setOnClickListener {
                onHabitCompleted(habit)
            }
            binding.btnDelete.setOnClickListener {
                onHabitDeleted(habit)
            }
        }
    }

    private class HabitDiffCallback : DiffUtil.ItemCallback<com.progressly.app.data.database.entities.HabitEntity>() {
        override fun areItemsTheSame(oldItem: com.progressly.app.data.database.entities.HabitEntity, newItem: com.progressly.app.data.database.entities.HabitEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: com.progressly.app.data.database.entities.HabitEntity, newItem: com.progressly.app.data.database.entities.HabitEntity) = oldItem == newItem
    }
}
