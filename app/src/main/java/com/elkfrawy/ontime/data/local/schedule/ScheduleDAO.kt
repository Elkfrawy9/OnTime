package com.elkfrawy.ontime.data.local.schedule

import androidx.room.*
import com.elkfrawy.ontime.domain.model.Schedule
import kotlinx.coroutines.flow.Flow


@Dao
interface ScheduleDAO {

    @Insert
    suspend fun insert(schedule: Schedule) : Long

    @Update
    suspend fun update(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)

    @Query("SELECT * FROM schedule ORDER BY schedule_date")
    fun getSchedules():Flow<List<Schedule>>

}