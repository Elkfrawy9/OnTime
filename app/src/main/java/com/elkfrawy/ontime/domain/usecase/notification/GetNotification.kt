package com.elkfrawy.ontime.domain.usecase.notification

import androidx.lifecycle.LiveData
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.model.Notification
import com.elkfrawy.ontime.domain.repository.NoteRepository
import com.elkfrawy.ontime.domain.repository.NotificationRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotification @Inject constructor(private val notificationRepo: NotificationRepository) {
     fun execute(): LiveData<List<Notification>> = notificationRepo.getNotificationLiveDate()
}