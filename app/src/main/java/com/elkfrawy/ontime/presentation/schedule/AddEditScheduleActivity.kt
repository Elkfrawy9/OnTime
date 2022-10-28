package com.elkfrawy.ontime.presentation.schedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.elkfrawy.ontime.R
import com.elkfrawy.ontime.data.constant.*
import com.elkfrawy.ontime.databinding.ActivityAddEditScheduleBinding
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.model.Schedule
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddEditScheduleActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    val viewModel: ScheduleViewModel by viewModels()
    lateinit var binding: ActivityAddEditScheduleBinding
    lateinit var adapter: ScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ScheduleAdapter(viewModel)

        if (savedInstanceState == null) {
            val intent = intent

            intent.let {
                viewModel.editSchedule = it.extras?.getParcelable(SCHEDULE_EXTRA_KEY)
                viewModel.state = it.extras?.getInt(SCHEDULE_STATE_EXTRA) ?: 0
                viewModel.updatePosition = it.extras?.getInt(UPDATE_SCHEDULE_KEY) ?: 0
                viewModel.listSize = it.extras?.getInt(LIST_SIZE_KEY) ?: -1
                if (viewModel.state == SCHEDULE_STATE_VALUE)
                    putData(viewModel.editSchedule!!)
            }
        }
        binding.apply {
            scheduleToolbar.setOnMenuItemClickListener(this@AddEditScheduleActivity)
            scheduleToolbar.setNavigationOnClickListener { onBackPressed() }
            doneSchedule.setOnClickListener {
                viewModel.checked = !viewModel.checked
            }
        }

        println(viewModel.reminderText)
        setViewDate()

    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.saveSchedule -> {
                val titleText = binding.edScheduleTitle.text.toString()

                if (titleText.isBlank())
                    Toast.makeText(this@AddEditScheduleActivity, "You must add a title", Toast.LENGTH_LONG).show()
                else {
                    val placeText = binding.edSchedulePlace.text.toString()
                    val noteText = binding.edScheduleNote.text.toString()

                    val gDate = GregorianCalendar(
                        viewModel.calendar.get(Calendar.YEAR),
                        viewModel.calendar.get(Calendar.MONTH),
                        viewModel.calendar.get(Calendar.DAY_OF_MONTH),
                        0,
                        0,
                        0).time

                    viewModel.calendar.time = gDate
                    if (viewModel.state == SCHEDULE_STATE_VALUE) {
                        updateSchedule(titleText, gDate, placeText, noteText)
                    } else
                        addSchedule(titleText, gDate, placeText, noteText)
                }
            }
        }
        return true
    }

    private fun setViewDate() {
        binding.apply {
            pickDate.text = toStringDate(viewModel.calendar.time)
            pickStartTime.text = toStringTime(viewModel.startTime.time)
            pickFinishTime.text = toStringTime(viewModel.finishTime.time)
            doneSchedule.isChecked = viewModel.checked
            pickReminder.text = viewModel.reminderText
        }
    }

    fun onDatePicker(view: View) {

        val date = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(viewModel.calendar.timeInMillis)
            .build()

        date.show(supportFragmentManager, "date")

        date.addOnPositiveButtonClickListener {
            viewModel.calendar.timeInMillis = it
            binding.pickDate.text = toStringDate(viewModel.calendar.time)
        }
        date.addOnNegativeButtonClickListener { date.dismiss() }
        date.addOnDismissListener { date.dismiss() }

    }

    private fun putData(schedule: Schedule) {

        binding.apply {
            edScheduleTitle.setText(schedule.title)
            edSchedulePlace.setText(schedule.place)
            edScheduleNote.setText(schedule.note)
            viewModel.calendar.time = schedule.date
            viewModel.startTime.time = schedule.startFrom
            viewModel.finishTime.time = schedule.finish
            date.text = toStringDate(viewModel.calendar.time)
            startFrom.text = toStringTime(viewModel.startTime.time)
            finish.text = toStringTime(viewModel.finishTime.time)
            viewModel.checked = schedule.checked
            doneSchedule.isChecked = viewModel.checked
            viewModel.reminderText =
                if (schedule.reminder == 0) "On Time" else "${schedule.reminder} minutes before"

        }
    }

    private fun addSchedule(titleText: String, gDate: Date, place: String, note: String) {

        viewModel.executeInsertDate(Dates(viewModel.calendar.time))
        val schedule = Schedule(
            title = titleText,
            date = gDate,
            startFrom = viewModel.startTime.time,
            finish = viewModel.finishTime.time,
            place = place,
            note = note,
            checked = viewModel.checked,
            reminder = viewModel.reminder,
        )
        runBlocking {
            val id = viewModel.executeInsertSchedule(schedule)
            setAlarm(titleText, place, id)
        }

        onBackPressed()
    }

    private fun updateSchedule(titleText: String, gDate: Date, placeText: String, noteText: String)  {

        val oldDate = viewModel.editSchedule?.date
        viewModel.editSchedule?.apply {

            val newSchedule = Schedule(this.id, titleText, gDate, viewModel.startTime.time, viewModel.finishTime.time,
                placeText, noteText, viewModel.checked, viewModel.reminder)

            if (viewModel.listSize == 1)
                viewModel.executeUpdateDate(oldDate!!, gDate)

            viewModel.executeCancelAlarm(this.id)
            setAlarm(titleText, placeText, this.id)
            viewModel.executeUpdateSchedule(newSchedule)

        }

        onBackPressed()
    }
    private fun toStringTime(date: Date): String {
        val timeFormatter = SimpleDateFormat("hh:mm aa")
        return timeFormatter.format(date)
    }

    private fun toStringDate(date: Date): String =
        DateFormat.getDateInstance().format(date)

    fun onReminderClicked(view: View) {

        val items = arrayOf(
            "On time",
            "5 Minute before",
            "10 Minute before",
            "15 Minute before",
            "30 Minute before"
        )

        MaterialAlertDialogBuilder(this@AddEditScheduleActivity)
            .setTitle("Reminder")
            .setItems(items) { _, selected ->
                viewModel.reminderText = items[selected]
                binding.pickReminder.text = viewModel.reminderText
                if (selected == items.size - 1)
                    viewModel.reminder = 30
                else
                    viewModel.reminder = selected * 5
            }.show()
    }
    fun onStarTimePicker(view: View) {

        val startTime = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setMinute(
                viewModel.startTime?.get(Calendar.MINUTE) ?: Calendar.getInstance()
                    .get(Calendar.MINUTE)
            )
            .setHour(
                viewModel.startTime?.get(Calendar.HOUR_OF_DAY) ?: Calendar.getInstance()
                    .get(Calendar.HOUR_OF_DAY)
            )
            .setTitleText("Select start time")
            .build()

        startTime.show(supportFragmentManager, "startTime")

        startTime.addOnPositiveButtonClickListener {

            viewModel.finishTime.timeInMillis = viewModel.calendar.timeInMillis
            viewModel.startTime.set(Calendar.HOUR_OF_DAY, startTime.hour)
            viewModel.startTime.set(Calendar.MINUTE, startTime.minute)

            binding.pickStartTime.text = toStringTime(viewModel.startTime.time)

        }
        startTime.addOnNegativeButtonClickListener { startTime.dismiss() }
        startTime.addOnDismissListener { startTime.dismiss() }

    }
    fun onFinishTimePicker(view: View) {

        val finishTime = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setMinute(viewModel.finishTime.get(Calendar.MINUTE))
            .setHour(viewModel.finishTime.get(Calendar.HOUR_OF_DAY))
            .setTitleText("Select finish time")
            .build()

        finishTime.show(supportFragmentManager, "finishTime")

        finishTime.addOnPositiveButtonClickListener {
            viewModel.finishTime.timeInMillis = viewModel.calendar.timeInMillis
            viewModel.finishTime.set(Calendar.HOUR_OF_DAY, finishTime.hour)
            viewModel.finishTime.set(Calendar.MINUTE, finishTime.minute)

            binding.pickFinishTime.text = toStringTime(viewModel.finishTime.time)
        }
        finishTime.addOnNegativeButtonClickListener { finishTime.dismiss() }
        finishTime.addOnDismissListener { finishTime.dismiss() }
    }

    fun setAlarm(title:String, place: String, code: Long){
        viewModel.startTime.set(Calendar.MONTH, viewModel.calendar.get(Calendar.MONTH))
        viewModel.startTime.set(Calendar.YEAR, viewModel.calendar.get(Calendar.YEAR))
        viewModel.startTime.set(Calendar.SECOND, 0)
        viewModel.startTime.set(Calendar.DAY_OF_MONTH, viewModel.calendar.get(Calendar.DAY_OF_MONTH))
        viewModel.startTime.set(Calendar.MINUTE, (viewModel.startTime.get(Calendar.MINUTE) - viewModel.reminder))

        if (viewModel.startTime.get(Calendar.MINUTE) < 0) {
            viewModel.startTime.set(Calendar.MINUTE, viewModel.startTime.get(Calendar.MINUTE) + 60)
            viewModel.startTime.set(Calendar.HOUR, viewModel.startTime.get(Calendar.HOUR) - 1)

        }
        if (Calendar.getInstance().before(viewModel.startTime))
            viewModel.executeSetAlarm(viewModel.startTime, title, place, code)
        else
            Toast.makeText(this, "The time passed update the time to setup alarm", Toast.LENGTH_LONG).show()
    }

}