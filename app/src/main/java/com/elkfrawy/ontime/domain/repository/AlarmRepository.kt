package com.elkfrawy.ontime.domain.repository

import java.util.Calendar

interface AlarmRepository{

    fun setAlarm(calender: Calendar, title:String, place: String, code: Long)
    fun cancelAlarm(code: Long)

}