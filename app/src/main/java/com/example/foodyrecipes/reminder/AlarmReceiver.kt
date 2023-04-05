package com.example.foodyrecipes.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.foodyrecipes.util.Constants.REMINDER_INTENT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(REMINDER_INTENT_KEY) ?: return

        MakeNotification.showNotification(
            context = context!!,
            message = message
        )
    }
}