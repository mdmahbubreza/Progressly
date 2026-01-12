package com.progressly.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val completedDate: LocalDate? = null,
    val createdDate: LocalDate = LocalDate.now(),
    val category: String = "General"
)

@Entity(tableName = "task_streaks")
data class TaskStreakEntity(
    @PrimaryKey
    val date: LocalDate = LocalDate.now(),
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val weeklyStreak: Int = 0
)

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val calories: Int = 0,
    val mealType: String = "Breakfast", // Breakfast, Lunch, Dinner, Snack
    val loggedDate: LocalDate = LocalDate.now(),
    val time: String = ""
)

@Entity(tableName = "meal_streaks")
data class MealStreakEntity(
    @PrimaryKey
    val date: LocalDate = LocalDate.now(),
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val weeklyStreak: Int = 0
)

@Entity(tableName = "water_logs")
data class WaterLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int, // in ml
    val loggedDate: LocalDate = LocalDate.now(),
    val time: String = ""
)

@Entity(tableName = "water_streaks")
data class WaterStreakEntity(
    @PrimaryKey
    val date: LocalDate = LocalDate.now(),
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val weeklyStreak: Int = 0,
    val dailyGoal: Int = 2000 // ml
)

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String = "",
    val frequency: String = "Daily", // Daily, Weekly, Monthly
    val createdDate: LocalDate = LocalDate.now(),
    val category: String = "General"
)

@Entity(tableName = "habit_logs")
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitId: Int,
    val completedDate: LocalDate = LocalDate.now()
)

@Entity(tableName = "habit_streaks")
data class HabitStreakEntity(
    @PrimaryKey
    val habitId: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val weeklyStreak: Int = 0,
    val lastCompletedDate: LocalDate? = null
)
