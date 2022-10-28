package com.elkfrawy.ontime.data.local.date

import androidx.room.*
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.model.DateWithSchedule
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface DateDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dates: Dates)

    @Delete
    suspend fun delete(dates: Dates)

    @Transaction
    suspend fun update(oldDate:Date, newDate:Date){
        delete(Dates(oldDate));
        insert(Dates(newDate));
    }

    @Transaction
    @Query("SELECT * FROM date INNER JOIN schedule ON date.date = schedule.schedule_date ORDER BY date.date")
    fun getDateAndSchedules(): Flow<List<DateWithSchedule>>

    @Query("SELECT * FROM date")
    fun getDate(): List<Dates>
}