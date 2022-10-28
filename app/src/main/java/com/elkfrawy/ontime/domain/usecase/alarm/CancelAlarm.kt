package com.elkfrawy.ontime.domain.usecase.alarm

import com.elkfrawy.ontime.domain.repository.AlarmRepository
import javax.inject.Inject

class CancelAlarm @Inject constructor(private val alarmRepo: AlarmRepository) {
    fun execute(code: Long) = alarmRepo.cancelAlarm(code)
}