package com.elkfrawy.ontime.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notification")
data class Notification (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title: String,
    val date: String,
    )