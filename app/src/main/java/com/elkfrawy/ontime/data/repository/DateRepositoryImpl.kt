package com.elkfrawy.ontime.data.repository

import com.elkfrawy.ontime.data.local.date.DateLocalSource
import com.elkfrawy.ontime.data.local.note.NoteLocalSource
import com.elkfrawy.ontime.domain.model.DateWithSchedule
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.repository.DateRepository
import com.elkfrawy.ontime.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(private val dateSource: DateLocalSource) : DateRepository{

    override suspend fun insert(date: Dates) = dateSource.insert(date)
    override suspend fun delete(date: Dates) = dateSource.delete(date)
    override suspend fun updateTransaction(oldDate: Date, newDate: Date) = dateSource.updateTransaction(oldDate, newDate)
    override fun getDates(): List<Dates> = dateSource.getDates()
    override fun getDateWithSchedule(): Flow<List<DateWithSchedule>> = dateSource.getDateWithSchedule()

}