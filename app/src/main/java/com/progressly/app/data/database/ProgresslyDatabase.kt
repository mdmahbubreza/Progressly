package com.progressly.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.progressly.app.data.database.dao.*
import com.progressly.app.data.database.entities.*
import com.progressly.app.utils.LocalDateConverter

@Database(
    entities = [
        TaskEntity::class,
        TaskStreakEntity::class,
        MealEntity::class,
        MealStreakEntity::class,
        WaterLogEntity::class,
        WaterStreakEntity::class,
        HabitEntity::class,
        HabitLogEntity::class,
        HabitStreakEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class ProgresslyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun taskStreakDao(): TaskStreakDao
    abstract fun mealDao(): MealDao
    abstract fun mealStreakDao(): MealStreakDao
    abstract fun waterLogDao(): WaterLogDao
    abstract fun waterStreakDao(): WaterStreakDao
    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao
    abstract fun habitStreakDao(): HabitStreakDao

    companion object {
        @Volatile
        private var INSTANCE: ProgresslyDatabase? = null

        fun getDatabase(context: Context): ProgresslyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProgresslyDatabase::class.java,
                    "progressly_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
