package com.elkfrawy.ontime.domain.usecase.date

import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.repository.DateRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import javax.inject.Inject

class DeleteDate @Inject constructor(private val dateRepo: DateRepository){
     suspend fun execute(data: Dates) = dateRepo.delete(data)
}