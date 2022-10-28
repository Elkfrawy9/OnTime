package com.elkfrawy.ontime.data.local.date

import com.elkfrawy.ontime.domain.model.DateWithSchedule
import com.elkfrawy.ontime.domain.model.Dates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class DateLocalSourceImpl @Inject constructor(private val dateDao: DateDAO): DateLocalSource{

    override suspend fun insert(date: Dates) = withContext(Dispatchers.IO){
        dateDao.insert(date)
    }

    override suspend fun delete(date: Dates) = withContext(Dispatchers.IO){
        dateDao.delete(date)
    }

    override suspend fun updateTransaction(oldDate: Date, newDate: Date) = withContext(Dispatchers.IO){
        dateDao.update(oldDate, newDate)
    }

    override fun getDates(): List<Dates> = dateDao.getDate()


    override fun getDateWithSchedule(): Flow<List<DateWithSchedule>> = dateDao.getDateAndSchedules()


}