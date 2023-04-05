package com.example.foodyrecipes.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.foodyrecipes.util.Constants.REMINDER_INTENT_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(message: String, timeInMillis: Long){
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(REMINDER_INTENT_KEY, message)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            PendingIntent.getBroadcast(
                context,
                200,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel(){
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                200,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


}