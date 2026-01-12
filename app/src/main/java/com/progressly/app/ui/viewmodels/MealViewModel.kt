package com.progressly.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.progressly.app.data.database.entities.*
import com.progressly.app.data.repositories.MealRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MealViewModel(private val repository: MealRepository) : ViewModel() {

    fun getMealsByDate(date: LocalDate) = repository.getMealsByDate(date).asLiveData()

    fun getMealsCountByDate(date: LocalDate) = repository.getMealsCountByDate(date).asLiveData()

    fun getTotalCaloriesByDate(date: LocalDate) = repository.getTotalCaloriesByDate(date).asLiveData()

    fun getMealStreak(date: LocalDate) = repository.getMealStreak(date).asLiveData()

    fun addMeal(name: String, calories: Int = 0, mealType: String = "Breakfast") {
        viewModelScope.launch {
            val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            val meal = MealEntity(
                name = name,
                calories = calories,
                mealType = mealType,
                time = time
            )
            repository.insertMeal(meal)
        }
    }

    fun updateMeal(meal: MealEntity) {
        viewModelScope.launch {
            repository.updateMeal(meal)
        }
    }

    fun deleteMeal(meal: MealEntity) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }

    fun updateMealStreak(streak: MealStreakEntity) {
        viewModelScope.launch {
            repository.updateMealStreak(streak)
        }
    }
}

class MealViewModelFactory(private val repository: MealRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(repository) as T
    }
}
