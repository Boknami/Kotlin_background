package com.example.testworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class TestService : Service() {
    override fun onCreate() {
        super.onCreate()

        // From Oreo, notification channel must be set
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Test Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel("YOUR_CHANNEL_ID", name, importance)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // TODO: The comment intent below defines which activity to launch when the notification is clicked.
        // val notificationIntent = Intent(this, TestActivity::class.java)
        // val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val builder = NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
            .setSmallIcon(com.google.android.material.R.drawable.ic_keyboard_black_24dp) // Replace with your valid icon resource
            .setContentText("test")
        //.setContentIntent(pendingIntent)
        startForeground(1, builder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: Define the action to be performed when the service is first started.
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        // TODO: Things to do when the service ends
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
