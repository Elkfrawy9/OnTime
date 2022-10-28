package com.elkfrawy.ontime.domain.usecase.alarm

import com.elkfrawy.ontime.domain.repository.AlarmRepository
import dagger.multibindings.IntKey
import java.util.*
import javax.inject.Inject

class SetAlarm @Inject constructor(private val alarmRepo:AlarmRepository) {

    fun execute(calender: Calendar, title: String, place: String, code: Long) =
        alarmRepo.setAlarm(calender, title, place, code)

}