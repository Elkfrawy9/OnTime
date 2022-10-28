package com.elkfrawy.ontime.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*


@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title:String,
    val text:String,
    val date: Date,
    val pin:Boolean = false
): Parcelable