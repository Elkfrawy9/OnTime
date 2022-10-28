package com.elkfrawy.ontime.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.elkfrawy.ontime.R
import com.elkfrawy.ontime.data.constant.*
import com.elkfrawy.ontime.presentation.main.MainActivity

class AlarmCast : BroadcastReceiver() {

    override fun onReceive(p0: Context, p1: Intent) {

        val title = p1.extras?.getString(NOTIFICATION_TITLE, "") ?: ""
        val place = p1.extras?.getString(NOTIFICATION_PLACE, "") ?: ""
        val date = p1.extras?.getString(NOTIFICATION_DATE, "") ?: ""

        val service = Intent(p0, InsertNotificationService::class.java)
        service.putExtra(WORKER_TITLE, title)
        service.putExtra(WORKER_DATE, date)
        p0.startService(service)

        val activityIntent = Intent(p0, MainActivity::class.java)
        activityIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pi = PendingIntent.getActivity(p0, 0, activityIntent, 0)
        val notification = NotificationCompat.Builder(p0, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(place)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .setSmallIcon(R.mipmap.ic_launcher_logo)
            .build()

        val managerCompat = NotificationManagerCompat.from(p0)
        managerCompat.notify(NOTIFICATION_ID, notification)
    }
}