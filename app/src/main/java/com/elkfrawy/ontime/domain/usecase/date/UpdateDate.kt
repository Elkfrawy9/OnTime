package com.elkfrawy.ontime.domain.usecase.date

import com.elkfrawy.ontime.domain.repository.DateRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import java.util.Date
import javax.inject.Inject

class UpdateDate @Inject constructor(private val dateRepo: DateRepository){
     suspend fun execute(data1: Date, data2: Date) = dateRepo.updateTransaction(data1, data2)
}