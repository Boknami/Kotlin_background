package com.example.testworkmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationService : Service() {
    val TAG = "로그"
    private lateinit var handler: Handler
    private val notificationId = 1
    private val notificationChannelId = "notification_channel"
    private val notificationInterval = 10000 // 10 seconds

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        handler = Handler()
        createNotificationChannel()
        startNotificationLoop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind")
        return null
    }

    private fun createNotificationChannel() {
        Log.d(TAG, "createNotificationChannel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun startNotificationLoop() {
        Log.d(TAG, "startNotificationLoop")
        handler.postDelayed(notificationRunnable, notificationInterval.toLong())
    }

    private val notificationRunnable = object : Runnable {
        override fun run() {
            Log.d(TAG, "notificationRunnable")
            createNotification()
            handler.postDelayed(this, notificationInterval.toLong())
        }
    }

    private fun createNotification() {
        Log.d(TAG, "createNotification")
        val notification = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Interval Notification")
            .setContentText("This is a notification created every 10 seconds.")
            .setSmallIcon(R.drawable.icon) // Replace with your icon
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        handler.removeCallbacks(notificationRunnable)
    }
}
