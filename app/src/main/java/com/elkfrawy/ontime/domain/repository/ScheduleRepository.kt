package com.elkfrawy.ontime.domain.repository

import com.elkfrawy.ontime.domain.model.Schedule

interface ScheduleRepository {

    suspend fun update(schedule: Schedule)
    suspend fun delete(schedule: Schedule)
    suspend fun insert(schedule: Schedule):Long

}