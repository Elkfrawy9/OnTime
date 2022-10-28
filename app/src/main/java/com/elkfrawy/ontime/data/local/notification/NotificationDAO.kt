package com.elkfrawy.ontime.data.local.notification

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elkfrawy.ontime.domain.model.Notification
import kotlinx.coroutines.flow.Flow


@Dao
interface NotificationDAO {

    @Insert
    fun insert(notification: Notification)

    @Update
    fun update(notification: Notification)

    @Delete
    fun delete(notification: Notification)

    @Delete
    fun deleteAll(notifications: List<Notification>)

    @Query("SELECT * FROM notification")
    fun getNotifications(): LiveData<List<Notification>>

}