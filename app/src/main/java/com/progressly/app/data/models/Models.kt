package com.progressly.app.data.models

import java.time.LocalDate

data class UserProfile(
    val name: String,
    val age: Int,
    val gender: String,
    val weight: Int,
    val notificationsEnabled: Boolean = true
)

data class TaskModel(
    val id: Int,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val completedDate: LocalDate? = null,
    val createdDate: LocalDate = LocalDate.now(),
    val category: String = "General"
)

data class MealModel(
    val id: Int,
    val name: String,
    val calories: Int = 0,
    val mealType: String = "Breakfast",
    val loggedDate: LocalDate = LocalDate.now(),
    val time: String = ""
)

data class WaterLogModel(
    val id: Int,
    val amount: Int,
    val loggedDate: LocalDate = LocalDate.now(),
    val time: String = ""
)

data class HabitModel(
    val id: Int,
    val name: String,
    val description: String = "",
    val frequency: String = "Daily",
    val createdDate: LocalDate = LocalDate.now(),
    val category: String = "General"
)

data class StreakData(
    val dailyStreak: Int = 0,
    val weeklyStreak: Int = 0,
    val longestStreak: Int = 0
)

data class DailySummary(
    val date: LocalDate = LocalDate.now(),
    val tasksCompleted: Int = 0,
    val mealsLogged: Int = 0,
    val waterIntake: Int = 0,
    val habitsCompleted: Int = 0,
    val totalCalories: Int = 0
)
