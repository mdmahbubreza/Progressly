package com.progressly.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.progressly.app.data.database.entities.*
import com.progressly.app.data.repositories.HabitRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {

    fun getAllHabits() = repository.getAllHabits().asLiveData()

    fun getHabitsCount() = repository.getHabitsCount().asLiveData()

    fun getCompletedHabitsCountByDate(date: LocalDate) = repository.getCompletedHabitsCountByDate(date).asLiveData()

    fun getHabitStreak(habitId: Int) = repository.getHabitStreak(habitId).asLiveData()

    fun getAllStreaks() = repository.getAllStreaks().asLiveData()

    fun addHabit(name: String, description: String = "", frequency: String = "Daily", category: String = "General") {
        viewModelScope.launch {
            val habit = HabitEntity(
                name = name,
                description = description,
                frequency = frequency,
                category = category
            )
            repository.insertHabit(habit)
        }
    }

    fun completeHabit(habitId: Int, date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            repository.logHabitCompletion(habitId, date)
        }
    }

    fun updateHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun updateHabitStreak(streak: HabitStreakEntity) {
        viewModelScope.launch {
            repository.updateHabitStreak(streak)
        }
    }

    fun isHabitCompletedOnDate(habitId: Int, date: LocalDate) = repository.isHabitCompletedOnDate(habitId, date).asLiveData()
}

class HabitViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitViewModel(repository) as T
    }
}
