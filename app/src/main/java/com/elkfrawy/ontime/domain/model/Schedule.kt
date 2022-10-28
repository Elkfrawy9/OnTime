package com.elkfrawy.ontime.domain.model

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.parcelize.Parcelize
import java.util.*


@Entity(tableName = "schedule")
@Parcelize
data class Schedule(

    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val title:String,
    @ColumnInfo(name = "schedule_date")
    val date: Date,
    val startFrom: Date,
    val finish: Date,
    val place:String,
    val note:String,
    val checked:Boolean = false,
    val reminder:Int = 0,

    ):Parcelable


