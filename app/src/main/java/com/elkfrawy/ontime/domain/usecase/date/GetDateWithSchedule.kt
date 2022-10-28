package com.elkfrawy.ontime.domain.usecase.date

import com.elkfrawy.ontime.domain.model.DateWithSchedule
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.repository.DateRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDateWithSchedule @Inject constructor(private val dateRepo: DateRepository) {
     fun execute(): Flow<List<DateWithSchedule>> = dateRepo.getDateWithSchedule()
}