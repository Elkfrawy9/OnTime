package com.elkfrawy.ontime.service.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.elkfrawy.ontime.data.constant.NOTIFICATION_DATE
import com.elkfrawy.ontime.data.constant.NOTIFICATION_PLACE
import com.elkfrawy.ontime.data.constant.NOTIFICATION_TITLE
import com.elkfrawy.ontime.domain.repository.AlarmRepository
import com.elkfrawy.ontime.service.AlarmCast
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DateFormat
import java.util.*
import javax.inject.Inject


class AlarmRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : AlarmRepository {

    override fun setAlarm(calender: Calendar, title: String, place: String, code: Long) {
        val alarm = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmCast::class.java)
        intent.putExtra(NOTIFICATION_TITLE, title)
        intent.putExtra(NOTIFICATION_PLACE, place)
        intent.putExtra(
            NOTIFICATION_DATE,
            DateFormat.getDateInstance().format(calender.time)
        )
        val pendingIntent =
            PendingIntent.getBroadcast(context, code.toInt(), intent, PendingIntent.FLAG_IMMUTABLE or  PendingIntent.FLAG_ONE_SHOT)
        alarm.setExact(AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent)
    }
    override fun cancelAlarm(code: Long) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManager::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, code.toInt(), intent, PendingIntent.FLAG_IMMUTABLE or  PendingIntent.FLAG_ONE_SHOT)
        alarm.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}