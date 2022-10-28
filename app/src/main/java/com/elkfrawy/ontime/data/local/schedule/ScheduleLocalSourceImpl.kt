package com.elkfrawy.ontime.data.local.schedule

import com.elkfrawy.ontime.domain.model.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleLocalSourceImpl @Inject constructor(private val scheduleDAO: ScheduleDAO) : ScheduleLocalSource{

    override suspend fun update(schedule: Schedule) = withContext(Dispatchers.IO){
        scheduleDAO.update(schedule)
    }

    override suspend fun delete(schedule: Schedule) = withContext(Dispatchers.IO){
        scheduleDAO.delete(schedule)
    }

    override suspend fun insert(schedule: Schedule): Long = withContext(Dispatchers.IO){
        scheduleDAO.insert(schedule)
    }
}