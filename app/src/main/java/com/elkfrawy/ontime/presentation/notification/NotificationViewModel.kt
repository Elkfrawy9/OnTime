package com.elkfrawy.ontime.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elkfrawy.ontime.domain.model.Notification
import com.elkfrawy.ontime.domain.usecase.notification.DeleteAllNotification
import com.elkfrawy.ontime.domain.usecase.notification.DeleteNotification
import com.elkfrawy.ontime.domain.usecase.notification.GetNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val deleteNotification: DeleteNotification,
    private val deleteAllNotification: DeleteAllNotification,
    private val getNotification: GetNotification
) : ViewModel() {

    val notifications = getNotification.execute()

    fun executeDeleteAll(notifications: List<Notification>){
        viewModelScope.launch {
            deleteAllNotification.execute(notifications)
        }
    }

    fun executeDelete(notification: Notification){
        viewModelScope.launch {
            deleteNotification.execute(notification)
        }
    }
}