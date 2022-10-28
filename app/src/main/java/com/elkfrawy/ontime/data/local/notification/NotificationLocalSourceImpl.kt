package com.elkfrawy.ontime.data.local.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.elkfrawy.ontime.domain.model.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationLocalSourceImpl @Inject constructor(private val notificationDAO: NotificationDAO): NotificationLocalSource {

    override suspend fun insert(notification: Notification) = withContext(Dispatchers.IO){
        notificationDAO.insert(notification)
    }

    override suspend fun delete(notification: Notification) = withContext(Dispatchers.IO){
        notificationDAO.delete(notification)
    }

    override suspend fun deleteAll(notifications: List<Notification>) = withContext(Dispatchers.IO){
        notificationDAO.deleteAll(notifications)
    }

    override fun getNotificationLiveDate(): LiveData<List<Notification>> = notificationDAO.getNotifications()
}