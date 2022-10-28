package com.elkfrawy.ontime.data.util

import androidx.room.TypeConverter
import java.util.*


class DateConverter {

    @TypeConverter
    fun toDatabase(date:Date) = date.time

    @TypeConverter
    fun fromDatabase(time:Long) = Date(time)

}