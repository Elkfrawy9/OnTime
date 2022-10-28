package com.elkfrawy.ontime.presentation.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.elkfrawy.ontime.domain.model.DateWithSchedule
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.model.Schedule
import com.elkfrawy.ontime.domain.usecase.alarm.CancelAlarm
import com.elkfrawy.ontime.domain.usecase.alarm.SetAlarm
import com.elkfrawy.ontime.domain.usecase.date.*
import com.elkfrawy.ontime.domain.usecase.schedule.DeleteSchedule
import com.elkfrawy.ontime.domain.usecase.schedule.InsertSchedule
import com.elkfrawy.ontime.domain.usecase.schedule.UpdateSchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val insertSchedule: InsertSchedule,
    private val deleteSchedule: DeleteSchedule,
    private val updateSchedule: UpdateSchedule,
    private val deleteDate: DeleteDate,
    private val getDates: GetDates,
    private val insertDate: InsertDate,
    private val updateDate: UpdateDate,
    private val getDateWithSchedule: GetDateWithSchedule,
    private val setAlarm: SetAlarm,
    private val cancelAlarm: CancelAlarm,
) : ViewModel() {

    var updatePosition = 0
    var listSize = -1
    var state = 0
    var editSchedule: Schedule? = null
    var schedule = ""
    var calendar: Calendar = Calendar.getInstance()
    var startTime = Calendar.getInstance()
    var finishTime = Calendar.getInstance()
    var checked = false
    var reminder = 0
    var reminderText = "On time"

    suspend fun executeInsertSchedule(schedule: Schedule): Long = insertSchedule.execute(schedule)

    fun executeDeleteSchedule(schedule: Schedule) {
        viewModelScope.launch {
            deleteSchedule.execute(schedule)
        }
    }

    var dateList: List<Dates> = ArrayList<Dates>()
     fun getDates() = getDates.execute()

    val dateWithSchedule: LiveData<List<DateWithSchedule>> =
        getDateWithSchedule.execute().asLiveData()

    fun executeUpdateSchedule(schedule: Schedule) {
        viewModelScope.launch {
            updateSchedule.execute(schedule)
        }
    }

    fun executeInsertDate(date: Dates) {
        viewModelScope.launch {
            insertDate.execute(date)
        }
    }

    fun executeUpdateDate(oldDate: Date, newDate: Date) {
        viewModelScope.launch {
            updateDate.execute(oldDate, newDate)
        }
    }

    fun insertSchedule(schedule: Schedule) {
        viewModelScope.launch {
            insertSchedule.execute(schedule)
        }
    }


    fun executeDeleteDate(date: Dates) {
        viewModelScope.launch {
            deleteDate.execute(date)
        }
    }

    fun executeSetAlarm(calender: Calendar, title: String, place: String, code: Long){
        setAlarm.execute(calender, title, place, code)
    }
    fun executeCancelAlarm(code: Long){
        cancelAlarm.execute(code)
    }


}