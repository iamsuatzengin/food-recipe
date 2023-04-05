package com.example.foodyrecipes.reminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.foodyrecipes.R

object MakeNotification {
    private const val CHANNEL_ID = "RECIPE_CHANNEL_ID"
    private const val NOTIFICATION_ID = 1
    private const val CHANNEL_NAME = "Foody_Channel"
    private const val CHANNEL_DESCRIPTION = "Foody_Channel_Description"
    private const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH

    @SuppressLint("MissingPermission")
    fun showNotification(context: Context, message: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_food_joke_24)
            .setContentTitle("Foody")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)){
            notify(NOTIFICATION_ID, builder.build())
        }

    }
}