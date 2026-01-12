package com.progressly.app.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.progressly.app.data.models.UserProfile
import java.time.LocalDate

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "progressly_prefs",
        Context.MODE_PRIVATE
    )

    // Onboarding
    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean("onboarding_completed", completed).apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean("onboarding_completed", false)
    }

    // User Profile
    fun setUserProfile(profile: UserProfile) {
        sharedPreferences.edit().apply {
            putString("user_name", profile.name)
            putInt("user_age", profile.age)
            putString("user_gender", profile.gender)
            putInt("user_weight", profile.weight)
            putBoolean("notifications_enabled", profile.notificationsEnabled)
        }.apply()
    }

    fun getUserProfile(): UserProfile {
        return UserProfile(
            name = sharedPreferences.getString("user_name", "") ?: "",
            age = sharedPreferences.getInt("user_age", 0),
            gender = sharedPreferences.getString("user_gender", "") ?: "",
            weight = sharedPreferences.getInt("user_weight", 0),
            notificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true)
        )
    }

    fun getUserName(): String {
        return sharedPreferences.getString("user_name", "User") ?: "User"
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode_enabled", enabled).apply()
    }

    fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean("dark_mode_enabled", true)
    }

    // Water Goal
    fun calculateDailyWaterGoal(age: Int, gender: String, weight: Int): Int {
        return if (weight > 0) {
            // Formula: weight * 35 ml (with slight variations for gender)
            val baseGoal = weight * 35
            when (gender.lowercase()) {
                "female" -> (baseGoal * 0.95).toInt() // 5% less for females
                else -> baseGoal
            }
        } else {
            2000 // Default 2L
        }
    }

    fun setDailyWaterGoal(goal: Int) {
        sharedPreferences.edit().putInt("daily_water_goal", goal).apply()
    }

    fun getDailyWaterGoal(): Int {
        return sharedPreferences.getInt("daily_water_goal", 2000)
    }

    // Last Sync Date
    fun setLastSyncDate(date: LocalDate) {
        sharedPreferences.edit().putString("last_sync_date", date.toString()).apply()
    }

    fun getLastSyncDate(): LocalDate? {
        val dateStr = sharedPreferences.getString("last_sync_date", null)
        return dateStr?.let { LocalDate.parse(it) }
    }

    // Notifications
    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun areNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean("notifications_enabled", true)
    }

    fun setWaterReminderTime(hour: Int, minute: Int) {
        sharedPreferences.edit().apply {
            putInt("water_reminder_hour", hour)
            putInt("water_reminder_minute", minute)
        }.apply()
    }

    fun getWaterReminderTime(): Pair<Int, Int> {
        val hour = sharedPreferences.getInt("water_reminder_hour", 9)
        val minute = sharedPreferences.getInt("water_reminder_minute", 0)
        return Pair(hour, minute)
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}
