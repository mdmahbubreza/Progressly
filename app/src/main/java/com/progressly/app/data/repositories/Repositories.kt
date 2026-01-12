package com.progressly.app.data.repositories

import com.progressly.app.data.database.dao.*
import com.progressly.app.data.database.entities.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskRepository(
    private val taskDao: TaskDao,
    private val taskStreakDao: TaskStreakDao
) {
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> {
        return taskDao.getTasksByDate(date)
    }

    fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    fun getTaskById(id: Int): Flow<TaskEntity> {
        return taskDao.getTaskById(id)
    }

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    fun getCompletedTasksCount(date: LocalDate): Flow<Int> {
        return taskDao.getCompletedTasksCount(date)
    }

    fun getTaskStreak(date: LocalDate): Flow<TaskStreakEntity?> {
        return taskStreakDao.getStreakByDate(date)
    }

    suspend fun updateTaskStreak(streak: TaskStreakEntity) {
        taskStreakDao.insertStreak(streak)
    }
}

class MealRepository(
    private val mealDao: MealDao,
    private val mealStreakDao: MealStreakDao
) {
    fun getMealsByDate(date: LocalDate): Flow<List<MealEntity>> {
        return mealDao.getMealsByDate(date)
    }

    fun getAllMeals(): Flow<List<MealEntity>> {
        return mealDao.getAllMeals()
    }

    suspend fun insertMeal(meal: MealEntity) {
        mealDao.insertMeal(meal)
    }

    suspend fun updateMeal(meal: MealEntity) {
        mealDao.updateMeal(meal)
    }

    suspend fun deleteMeal(meal: MealEntity) {
        mealDao.deleteMeal(meal)
    }

    fun getMealsCountByDate(date: LocalDate): Flow<Int> {
        return mealDao.getMealsCountByDate(date)
    }

    fun getTotalCaloriesByDate(date: LocalDate): Flow<Int> {
        return mealDao.getTotalCaloriesByDate(date)
    }

    fun getMealStreak(date: LocalDate): Flow<MealStreakEntity?> {
        return mealStreakDao.getStreakByDate(date)
    }

    suspend fun updateMealStreak(streak: MealStreakEntity) {
        mealStreakDao.insertStreak(streak)
    }
}

class WaterRepository(
    private val waterLogDao: WaterLogDao,
    private val waterStreakDao: WaterStreakDao
) {
    fun getWaterLogsByDate(date: LocalDate): Flow<List<WaterLogEntity>> {
        return waterLogDao.getWaterLogsByDate(date)
    }

    fun getTotalWaterByDate(date: LocalDate): Flow<Int?> {
        return waterLogDao.getTotalWaterByDate(date)
    }

    fun getAllWaterLogs(): Flow<List<WaterLogEntity>> {
        return waterLogDao.getAllWaterLogs()
    }

    suspend fun insertWaterLog(log: WaterLogEntity) {
        waterLogDao.insertWaterLog(log)
    }

    suspend fun deleteWaterLog(log: WaterLogEntity) {
        waterLogDao.deleteWaterLog(log)
    }

    fun getWaterStreak(date: LocalDate): Flow<WaterStreakEntity?> {
        return waterStreakDao.getStreakByDate(date)
    }

    suspend fun updateWaterStreak(streak: WaterStreakEntity) {
        waterStreakDao.insertStreak(streak)
    }

    fun getLatestWaterStreak(): Flow<WaterStreakEntity?> {
        return waterStreakDao.getLatestStreak()
    }
}

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitLogDao: HabitLogDao,
    private val habitStreakDao: HabitStreakDao
) {
    fun getAllHabits(): Flow<List<HabitEntity>> {
        return habitDao.getAllHabits()
    }

    fun getHabitById(id: Int): Flow<HabitEntity> {
        return habitDao.getHabitById(id)
    }

    suspend fun insertHabit(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    fun getHabitsCount(): Flow<Int> {
        return habitDao.getHabitsCount()
    }

    suspend fun logHabitCompletion(habitId: Int, date: LocalDate) {
        habitLogDao.insertHabitLog(HabitLogEntity(habitId = habitId, completedDate = date))
    }

    fun isHabitCompletedOnDate(habitId: Int, date: LocalDate): Flow<Int> {
        return habitLogDao.isHabitCompletedOnDate(habitId, date)
    }

    fun getCompletedHabitsCountByDate(date: LocalDate): Flow<Int> {
        return habitLogDao.getCompletedHabitsCountByDate(date)
    }

    fun getHabitStreak(habitId: Int): Flow<HabitStreakEntity?> {
        return habitStreakDao.getStreakByHabitId(habitId)
    }

    suspend fun updateHabitStreak(streak: HabitStreakEntity) {
        habitStreakDao.insertStreak(streak)
    }

    fun getAllStreaks(): Flow<List<HabitStreakEntity>> {
        return habitStreakDao.getAllStreaks()
    }
}
