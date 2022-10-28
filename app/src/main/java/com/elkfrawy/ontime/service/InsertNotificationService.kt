package com.elkfrawy.ontime.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.elkfrawy.ontime.data.constant.WORKER_DATE
import com.elkfrawy.ontime.data.constant.WORKER_TITLE
import com.elkfrawy.ontime.domain.model.Notification
import com.elkfrawy.ontime.domain.usecase.notification.InsertNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class InsertNotificationService : Service() {

    @Inject
    lateinit var insertNotification: InsertNotification

    override fun onBind(p0: Intent?): Nothing? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val title =  intent?.extras?.getString(WORKER_TITLE) ?: ""
        val date =  intent?.extras?.getString(WORKER_DATE) ?: ""

        CoroutineScope(Dispatchers.IO).launch{
            insertNotification.execute(Notification(title = title, date = date))
        }
        return START_NOT_STICKY
    }
}