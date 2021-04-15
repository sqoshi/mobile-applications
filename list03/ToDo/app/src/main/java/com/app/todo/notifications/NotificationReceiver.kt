package com.app.todo.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


/**
 * Responsible for handling background workflow.
 */
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotification()
    }
}