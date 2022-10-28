package com.elkfrawy.ontime.data.local.notification

import androidx.lifecycle.LiveData
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationLocalSource {

    suspend fun insert(notification: Notification)
    suspend fun delete(notification: Notification)
    suspend fun deleteAll(notifications: List<Notification>)
    fun getNotificationLiveDate(): LiveData<List<Notification>>



}