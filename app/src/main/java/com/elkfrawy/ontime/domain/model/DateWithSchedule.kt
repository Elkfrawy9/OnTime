package com.elkfrawy.ontime.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class DateWithSchedule(

    @Embedded val dates: Dates,
    @Relation(
        parentColumn = "date",
        entityColumn = "schedule_date"
    )
    val Schedules:List<Schedule>,

    )
