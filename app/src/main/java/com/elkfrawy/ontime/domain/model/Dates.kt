package com.elkfrawy.ontime.domain.model

import androidx.room.*
import java.util.*

@Entity(tableName = "date", indices = [Index(value = ["date"], unique = true)])
data class Dates(
    @PrimaryKey()
    val date:Date,
)
