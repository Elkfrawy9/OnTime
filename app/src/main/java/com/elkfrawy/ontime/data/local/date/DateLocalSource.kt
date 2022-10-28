package com.elkfrawy.ontime.data.local.date

import com.elkfrawy.ontime.domain.model.DateWithSchedule
import com.elkfrawy.ontime.domain.model.Dates
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DateLocalSource {

    suspend fun insert(date: Dates)
    suspend fun delete(date: Dates)
    suspend fun updateTransaction(oldDate: Date, newDate: Date)
    fun getDates():List<Dates>
    fun getDateWithSchedule():Flow<List<DateWithSchedule>>

}