package com.progressly.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.progressly.app.data.database.entities.*
import com.progressly.app.data.repositories.TaskRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    fun getTasksByDate(date: LocalDate) = repository.getTasksByDate(date).asLiveData()

    fun getAllTasks() = repository.getAllTasks().asLiveData()

    fun getCompletedTasksCount(date: LocalDate) = repository.getCompletedTasksCount(date).asLiveData()

    fun addTask(title: String, description: String = "", category: String = "General") {
        viewModelScope.launch {
            val task = TaskEntity(
                title = title,
                description = description,
                category = category
            )
            repository.insertTask(task)
        }
    }

    fun completeTask(task: TaskEntity) {
        viewModelScope.launch {
            val completedTask = task.copy(
                isCompleted = true,
                completedDate = LocalDate.now()
            )
            repository.updateTask(completedTask)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTaskStreak(streak: TaskStreakEntity) {
        viewModelScope.launch {
            repository.updateTaskStreak(streak)
        }
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}
