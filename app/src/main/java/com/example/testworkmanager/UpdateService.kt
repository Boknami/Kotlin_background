package com.example.testworkmanager
import android.app.*
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat

class UpdateService : Service() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var counter = 0
    private val updateInterval = 10000 // 10 seconds

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                updateTextViewValue()
                handler.postDelayed(this, updateInterval.toLong())
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        handler.postDelayed(runnable, updateInterval.toLong())
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "update_service_channel"
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Updating TextView")
            .setContentText("Updating TextView value every 10 seconds")
            .setSmallIcon(com.google.android.material.R.drawable.ic_keyboard_black_24dp)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Update Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }

        startForeground(1, notification)
    }

    private fun updateTextViewValue() {
        // Update the TextView's value
        counter++
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra(EXTRA_COUNTER, counter)
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    companion object {
        const val BROADCAST_ACTION = "update_broadcast_action"
        const val EXTRA_COUNTER = "extra_counter"
    }
}
