package com.elkfrawy.ontime.di

import com.elkfrawy.ontime.data.local.date.DateLocalSource
import com.elkfrawy.ontime.data.local.date.DateLocalSourceImpl
import com.elkfrawy.ontime.data.local.note.NoteLocalSource
import com.elkfrawy.ontime.data.local.note.NoteLocalSourceImpl
import com.elkfrawy.ontime.data.local.notification.NotificationLocalSource
import com.elkfrawy.ontime.data.local.notification.NotificationLocalSourceImpl
import com.elkfrawy.ontime.data.local.schedule.ScheduleLocalSource
import com.elkfrawy.ontime.data.local.schedule.ScheduleLocalSourceImpl
import com.elkfrawy.ontime.data.repository.DateRepositoryImpl
import com.elkfrawy.ontime.data.repository.NoteRepositoryImpl
import com.elkfrawy.ontime.data.repository.NotificationRepositoryImpl
import com.elkfrawy.ontime.data.repository.ScheduleRepositoryImpl
import com.elkfrawy.ontime.domain.repository.*
import com.elkfrawy.ontime.service.repository.AlarmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class InterfaceModule {

    @Singleton
    @Binds
    abstract fun provideDateSource(source: DateLocalSourceImpl):DateLocalSource

    @Singleton
    @Binds
    abstract fun provideScheduleSource(source: ScheduleLocalSourceImpl):ScheduleLocalSource

    @Singleton
    @Binds
    abstract fun provideNoteSource(source: NoteLocalSourceImpl):NoteLocalSource

    @Singleton
    @Binds
    abstract fun provideNotificationSource(source: NotificationLocalSourceImpl):NotificationLocalSource

    @Singleton
    @Binds
    abstract fun provideNotificationRepo(repo: NotificationRepositoryImpl):NotificationRepository

    @Singleton
    @Binds
    abstract fun provideScheduleRepo(repo: ScheduleRepositoryImpl):ScheduleRepository

    @Singleton
    @Binds
    abstract fun provideNoteRepo(repo: NoteRepositoryImpl):NoteRepository

    @Singleton
    @Binds
    abstract fun provideDateRepo(repo: DateRepositoryImpl):DateRepository

    @Singleton
    @Binds
    abstract fun provideAlarmRepo(repo: AlarmRepositoryImpl):AlarmRepository

}