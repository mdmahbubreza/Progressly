package com.progressly.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.progressly.app.data.preferences.PreferencesManager

class ProgresslyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        applyTheme()
    }

    private fun applyTheme() {
        val preferencesManager = PreferencesManager(this)
        val isDarkModeEnabled = preferencesManager.isDarkModeEnabled()
        
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
