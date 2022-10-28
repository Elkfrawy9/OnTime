package com.elkfrawy.ontime.domain.usecase

interface UseCase<O> {

    suspend fun execute():O

    interface OneParam<I, O>{
        suspend fun execute(data: I):O
    }

    interface TwoParam<I, P, O>{
        suspend fun execute(data1: I, data2: P):O
    }

}