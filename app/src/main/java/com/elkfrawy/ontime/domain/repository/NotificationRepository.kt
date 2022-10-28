package com.elkfrawy.ontime.domain.repository

import androidx.lifecycle.LiveData
import com.elkfrawy.ontime.domain.model.Notification

interface NotificationRepository {

    suspend fun insert(notification: Notification)
    suspend fun delete(notification: Notification)
    suspend fun deleteAll(notifications: List<Notification>)
    fun getNotificationLiveDate(): LiveData<List<Notification>>

}