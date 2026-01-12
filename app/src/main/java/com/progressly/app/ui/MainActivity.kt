package com.progressly.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.progressly.app.R
import com.progressly.app.data.database.ProgresslyDatabase
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.data.repositories.*
import com.progressly.app.databinding.ActivityMainBinding
import com.progressly.app.ui.fragments.*
import com.progressly.app.ui.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencesManager: PreferencesManager

    // Repositories
    private lateinit var taskRepository: TaskRepository
    private lateinit var mealRepository: MealRepository
    private lateinit var waterRepository: WaterRepository
    private lateinit var habitRepository: HabitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        // Apply dark mode if enabled
        if (!preferencesManager.isDarkModeEnabled()) {
            setTheme(R.style.Theme_Progressly)
        }

        // Check if onboarding is completed
        if (!preferencesManager.isOnboardingCompleted()) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            return
        }

        setupDatabase()
        setupNavigation()
    }

    private fun setupDatabase() {
        val database = ProgresslyDatabase.getDatabase(this)

        taskRepository = TaskRepository(database.taskDao(), database.taskStreakDao())
        mealRepository = MealRepository(database.mealDao(), database.mealStreakDao())
        waterRepository = WaterRepository(database.waterLogDao(), database.waterStreakDao())
        habitRepository = HabitRepository(
            database.habitDao(),
            database.habitLogDao(),
            database.habitStreakDao()
        )
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_tasks -> showFragment(TasksFragment())
                R.id.nav_meals -> showFragment(MealsFragment())
                R.id.nav_water -> showFragment(WaterFragment())
                R.id.nav_habits -> showFragment(HabitsFragment())
                R.id.nav_profile -> showFragment(ProfileFragment())
                else -> false
            }
            true
        }

        // Set default fragment
        if (savedInstanceState == null) {
            showFragment(TasksFragment())
            binding.bottomNavigation.selectedItemId = R.id.nav_tasks
        }
    }

    private fun showFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        return true
    }

    fun getTaskRepository() = taskRepository
    fun getMealRepository() = mealRepository
    fun getWaterRepository() = waterRepository
    fun getHabitRepository() = habitRepository
    fun getPreferencesManager() = preferencesManager
}
