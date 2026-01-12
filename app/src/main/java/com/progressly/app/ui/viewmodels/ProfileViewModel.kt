package com.progressly.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.progressly.app.data.models.DailySummary
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.data.repositories.*
import kotlinx.coroutines.flow.MutableLiveData
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProfileViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskRepository: TaskRepository,
    private val mealRepository: MealRepository,
    private val waterRepository: WaterRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _dailySummary = MutableLiveData<DailySummary>()
    val dailySummary = _dailySummary

    fun loadDailySummary(date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            combine(
                taskRepository.getCompletedTasksCount(date),
                mealRepository.getMealsCountByDate(date),
                waterRepository.getTotalWaterByDate(date),
                habitRepository.getCompletedHabitsCountByDate(date),
                mealRepository.getTotalCaloriesByDate(date)
            ) { tasksCompleted, mealsLogged, waterIntake, habitsCompleted, totalCalories ->
                DailySummary(
                    date = date,
                    tasksCompleted = tasksCompleted,
                    mealsLogged = mealsLogged,
                    waterIntake = waterIntake ?: 0,
                    habitsCompleted = habitsCompleted,
                    totalCalories = totalCalories ?: 0
                )
            }.asLiveData().value?.let { _dailySummary.postValue(it) }
        }
    }

    fun getUserName(): String {
        return preferencesManager.getUserName()
    }

    fun getTaskStreaks() = taskRepository.getTaskStreak(LocalDate.now()).asLiveData()

    fun getWaterStreaks() = waterRepository.getWaterStreak(LocalDate.now()).asLiveData()

    fun getMealStreaks() = mealRepository.getMealStreak(LocalDate.now()).asLiveData()

    fun getHabitStreaks() = habitRepository.getAllStreaks().asLiveData()

    fun getAllTasks() = taskRepository.getAllTasks().asLiveData()

    fun getAllHabits() = habitRepository.getAllHabits().asLiveData()
}

class ProfileViewModelFactory(
    private val preferencesManager: PreferencesManager,
    private val taskRepository: TaskRepository,
    private val mealRepository: MealRepository,
    private val waterRepository: WaterRepository,
    private val habitRepository: HabitRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            preferencesManager,
            taskRepository,
            mealRepository,
            waterRepository,
            habitRepository
        ) as T
    }
}
