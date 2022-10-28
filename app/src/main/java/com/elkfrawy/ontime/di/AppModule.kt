package com.elkfrawy.ontime.di

import android.content.Context
import androidx.room.Room
import com.elkfrawy.ontime.data.local.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDB::class.java,"on_time")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun getNoteDao(appDB: AppDB) = appDB.getNoteDAO()

    @Singleton
    @Provides
    fun getScheduleDao(appDB: AppDB) = appDB.getScheduleDAO()

    @Singleton
    @Provides
    fun getDateDao(appDB: AppDB) = appDB.getDateDAO()

    @Singleton
    @Provides
    fun getNotificationDao(appDB: AppDB) = appDB.getNotificationDAO()


    @Singleton
    @Provides
    fun applicationScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

}