package com.elkfrawy.ontime.data.local.schedule

import androidx.lifecycle.LiveData
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Notification
import com.elkfrawy.ontime.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleLocalSource {

    suspend fun update(schedule: Schedule)
    suspend fun delete(schedule: Schedule)
    suspend fun insert(schedule: Schedule):Long

}