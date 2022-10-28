package com.elkfrawy.ontime.data.repository

import com.elkfrawy.ontime.data.local.note.NoteLocalSource
import com.elkfrawy.ontime.data.local.schedule.ScheduleLocalSource
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Schedule
import com.elkfrawy.ontime.domain.repository.NoteRepository
import com.elkfrawy.ontime.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val scheduleSource: ScheduleLocalSource) : ScheduleRepository{

    override suspend fun update(schedule: Schedule) = scheduleSource.update(schedule)
    override suspend fun delete(schedule: Schedule) = scheduleSource.delete(schedule)
    override suspend fun insert(schedule: Schedule): Long = scheduleSource.insert(schedule)

}