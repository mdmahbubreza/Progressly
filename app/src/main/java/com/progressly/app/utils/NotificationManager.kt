package com.progressly.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.progressly.app.R

class NotificationManager(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val waterChannel = NotificationChannel(
                WATER_CHANNEL_ID,
                "Water Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders to drink water"
            }

            val habitChannel = NotificationChannel(
                HABIT_CHANNEL_ID,
                "Habit Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders for your daily habits"
            }

            val taskChannel = NotificationChannel(
                TASK_CHANNEL_ID,
                "Task Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Task completion reminders"
            }

            notificationManager.createNotificationChannel(waterChannel)
            notificationManager.createNotificationChannel(habitChannel)
            notificationManager.createNotificationChannel(taskChannel)
        }
    }

    fun showWaterReminder(message: String = "Time to drink water! 💧") {
        val notification = NotificationCompat.Builder(context, WATER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Water Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(WATER_NOTIFICATION_ID, notification)
    }

    fun showHabitReminder(habitName: String) {
        val notification = NotificationCompat.Builder(context, HABIT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Habit Time")
            .setContentText("Don't forget your '$habitName' habit! 🎯")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(HABIT_NOTIFICATION_ID, notification)
    }

    fun showTaskReminder(taskName: String) {
        val notification = NotificationCompat.Builder(context, TASK_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Task Reminder")
            .setContentText("Complete your task: $taskName ✓")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(TASK_NOTIFICATION_ID, notification)
    }

    fun showAchievementNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, HABIT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(ACHIEVEMENT_NOTIFICATION_ID, notification)
    }

    companion object {
        const val WATER_CHANNEL_ID = "water_channel"
        const val HABIT_CHANNEL_ID = "habit_channel"
        const val TASK_CHANNEL_ID = "task_channel"

        const val WATER_NOTIFICATION_ID = 1001
        const val HABIT_NOTIFICATION_ID = 1002
        const val TASK_NOTIFICATION_ID = 1003
        const val ACHIEVEMENT_NOTIFICATION_ID = 1004
    }
}

class NotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = NotificationManager(context)
        when (intent.action) {
            ACTION_WATER_REMINDER -> notificationManager.showWaterReminder()
            ACTION_HABIT_REMINDER -> {
                val habitName = intent.getStringExtra("habit_name") ?: "Habit"
                notificationManager.showHabitReminder(habitName)
            }
            ACTION_TASK_REMINDER -> {
                val taskName = intent.getStringExtra("task_name") ?: "Task"
                notificationManager.showTaskReminder(taskName)
            }
        }
    }

    companion object {
        const val ACTION_WATER_REMINDER = "com.progressly.app.WATER_REMINDER"
        const val ACTION_HABIT_REMINDER = "com.progressly.app.HABIT_REMINDER"
        const val ACTION_TASK_REMINDER = "com.progressly.app.TASK_REMINDER"
    }
}
