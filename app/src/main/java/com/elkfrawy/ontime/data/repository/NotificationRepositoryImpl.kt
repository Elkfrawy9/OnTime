package com.elkfrawy.ontime.data.repository

import androidx.lifecycle.LiveData
import com.elkfrawy.ontime.data.local.note.NoteLocalSource
import com.elkfrawy.ontime.data.local.notification.NotificationLocalSource
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Notification
import com.elkfrawy.ontime.domain.repository.NoteRepository
import com.elkfrawy.ontime.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val notificationSource: NotificationLocalSource) : NotificationRepository{

    override suspend fun insert(notification: Notification) = notificationSource.insert(notification)
    override suspend fun delete(notification: Notification) = notificationSource.delete(notification)
    override suspend fun deleteAll(notifications: List<Notification>) = notificationSource.deleteAll(notifications)
    override fun getNotificationLiveDate(): LiveData<List<Notification>> = notificationSource.getNotificationLiveDate()

}