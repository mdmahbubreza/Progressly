package com.progressly.app.data.database.dao

import androidx.room.*
import com.progressly.app.data.database.entities.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE createdDate = :date ORDER BY createdDate DESC")
    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY createdDate DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity>

    @Query("SELECT COUNT(*) FROM tasks WHERE completedDate = :date AND isCompleted = 1")
    fun getCompletedTasksCount(date: LocalDate): Flow<Int>
}

@Dao
interface TaskStreakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: TaskStreakEntity)

    @Update
    suspend fun updateStreak(streak: TaskStreakEntity)

    @Query("SELECT * FROM task_streaks WHERE date = :date")
    fun getStreakByDate(date: LocalDate): Flow<TaskStreakEntity?>

    @Query("SELECT * FROM task_streaks ORDER BY date DESC LIMIT 1")
    fun getLatestStreak(): Flow<TaskStreakEntity?>
}

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Update
    suspend fun updateMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)

    @Query("SELECT * FROM meals WHERE loggedDate = :date ORDER BY time DESC")
    fun getMealsByDate(date: LocalDate): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals ORDER BY loggedDate DESC")
    fun getAllMeals(): Flow<List<MealEntity>>

    @Query("SELECT COUNT(*) FROM meals WHERE loggedDate = :date")
    fun getMealsCountByDate(date: LocalDate): Flow<Int>

    @Query("SELECT SUM(calories) FROM meals WHERE loggedDate = :date")
    fun getTotalCaloriesByDate(date: LocalDate): Flow<Int>
}

@Dao
interface MealStreakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: MealStreakEntity)

    @Update
    suspend fun updateStreak(streak: MealStreakEntity)

    @Query("SELECT * FROM meal_streaks WHERE date = :date")
    fun getStreakByDate(date: LocalDate): Flow<MealStreakEntity?>

    @Query("SELECT * FROM meal_streaks ORDER BY date DESC LIMIT 1")
    fun getLatestStreak(): Flow<MealStreakEntity?>
}

@Dao
interface WaterLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterLog(waterLog: WaterLogEntity)

    @Delete
    suspend fun deleteWaterLog(waterLog: WaterLogEntity)

    @Query("SELECT * FROM water_logs WHERE loggedDate = :date ORDER BY time DESC")
    fun getWaterLogsByDate(date: LocalDate): Flow<List<WaterLogEntity>>

    @Query("SELECT SUM(amount) FROM water_logs WHERE loggedDate = :date")
    fun getTotalWaterByDate(date: LocalDate): Flow<Int?>

    @Query("SELECT * FROM water_logs ORDER BY loggedDate DESC")
    fun getAllWaterLogs(): Flow<List<WaterLogEntity>>
}

@Dao
interface WaterStreakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: WaterStreakEntity)

    @Update
    suspend fun updateStreak(streak: WaterStreakEntity)

    @Query("SELECT * FROM water_streaks WHERE date = :date")
    fun getStreakByDate(date: LocalDate): Flow<WaterStreakEntity?>

    @Query("SELECT * FROM water_streaks ORDER BY date DESC LIMIT 1")
    fun getLatestStreak(): Flow<WaterStreakEntity?>
}

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits ORDER BY createdDate DESC")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabitById(id: Int): Flow<HabitEntity>

    @Query("SELECT COUNT(*) FROM habits")
    fun getHabitsCount(): Flow<Int>
}

@Dao
interface HabitLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitLog(log: HabitLogEntity)

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId AND completedDate = :date")
    fun getHabitLogByDate(habitId: Int, date: LocalDate): Flow<HabitLogEntity?>

    @Query("SELECT COUNT(*) FROM habit_logs WHERE habitId = :habitId AND completedDate = :date")
    fun isHabitCompletedOnDate(habitId: Int, date: LocalDate): Flow<Int>

    @Query("SELECT COUNT(*) FROM habit_logs WHERE completedDate = :date")
    fun getCompletedHabitsCountByDate(date: LocalDate): Flow<Int>
}

@Dao
interface HabitStreakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: HabitStreakEntity)

    @Update
    suspend fun updateStreak(streak: HabitStreakEntity)

    @Query("SELECT * FROM habit_streaks WHERE habitId = :habitId")
    fun getStreakByHabitId(habitId: Int): Flow<HabitStreakEntity?>

    @Query("SELECT * FROM habit_streaks")
    fun getAllStreaks(): Flow<List<HabitStreakEntity>>
}
