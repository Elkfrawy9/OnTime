package com.elkfrawy.ontime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elkfrawy.ontime.data.util.DateConverter
import com.elkfrawy.ontime.data.local.date.DateDAO
import com.elkfrawy.ontime.data.local.note.NoteDAO
import com.elkfrawy.ontime.data.local.notification.NotificationDAO
import com.elkfrawy.ontime.data.local.schedule.ScheduleDAO
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Notification
import com.elkfrawy.ontime.domain.model.Schedule

@Database(entities = [Note::class, Schedule::class, Dates::class, Notification::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDB : RoomDatabase() {

    abstract fun getNoteDAO(): NoteDAO
    abstract fun getScheduleDAO(): ScheduleDAO
    abstract fun getDateDAO(): DateDAO
    abstract fun getNotificationDAO(): NotificationDAO

}