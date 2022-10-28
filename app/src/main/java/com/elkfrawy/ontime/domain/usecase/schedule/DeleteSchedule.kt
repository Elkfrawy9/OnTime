package com.elkfrawy.ontime.domain.usecase.schedule

import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Schedule
import com.elkfrawy.ontime.domain.repository.NoteRepository
import com.elkfrawy.ontime.domain.repository.ScheduleRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import javax.inject.Inject

class DeleteSchedule @Inject constructor(private val scheduleRepo: ScheduleRepository)  {
     suspend fun execute(data: Schedule) = scheduleRepo.delete(data)
}