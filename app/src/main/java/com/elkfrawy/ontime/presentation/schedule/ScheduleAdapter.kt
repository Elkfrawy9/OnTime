package com.elkfrawy.ontime.presentation.schedule

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.data.constant.*
import com.elkfrawy.ontime.databinding.ScheduleRecyclerViewBinding
import com.elkfrawy.ontime.domain.model.Dates
import com.elkfrawy.ontime.domain.model.Schedule
import java.text.SimpleDateFormat
import java.util.*


class ScheduleAdapter (val viewModel: ScheduleViewModel) :
    ListAdapter<Schedule, ScheduleAdapter.ScheduleViewHolder>(DiffCallBack()) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        deleteSchedule(recyclerView)
        super.onAttachedToRecyclerView(recyclerView)
    }

    private fun deleteSchedule(recyclerView: RecyclerView){
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val schedule = currentList[viewHolder.adapterPosition]
                if (currentList.size == 1)
                   viewModel.executeDeleteDate(Dates(schedule.date))
                Log.d("Elkfrawy9", "this is in ScheduleAdapter: deleting and cancel alarm")
                viewModel.executeCancelAlarm(schedule.id)
                viewModel.executeDeleteSchedule(schedule)
            }
        }).attachToRecyclerView(recyclerView)
    }

    private class DiffCallBack : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }
    }

    class ScheduleViewHolder(val binding: ScheduleRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ScheduleRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ScheduleViewHolder(binding)
        binding.root.setOnClickListener {
            val intent = Intent(parent.context, AddEditScheduleActivity::class.java)
            intent.putExtra(SCHEDULE_EXTRA_KEY, currentList[holder.adapterPosition])
            intent.putExtra(SCHEDULE_STATE_EXTRA, SCHEDULE_STATE_VALUE)
            intent.putExtra(UPDATE_SCHEDULE_KEY, holder.adapterPosition)
            intent.putExtra(LIST_SIZE_KEY, currentList.size)
            parent.context.startActivity(intent)
        }

        holder.binding.scheduleChecked.setOnClickListener {

            currentList[holder.adapterPosition].apply {
                val newSchedule = Schedule(id, title, date, startFrom, finish, place, note, !checked, reminder)
                val bool = newSchedule.checked
                if (newSchedule.checked) {
                    checkedCard(holder.binding, bool)
                    viewModel.executeUpdateSchedule(newSchedule)
                } else {
                    uncheckedCard(holder.binding, bool)
                    viewModel.executeUpdateSchedule(newSchedule)
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {

        val schedule = currentList[position]

        holder.binding.apply {
            tvScheduleTitle.text = schedule.title
            schedulePlace.text = schedule.place
            scheduleNote.text = schedule.note
        }

        val startTime = toStringTime(schedule.startFrom)
        var finishTime = ""
        if (schedule.finish != Date(0)) {
            finishTime = toStringTime(schedule.finish)
        }

        holder.binding.scheduleTime.text = "$startTime - $finishTime"
        if (schedule.checked) {
            checkedCard(holder.binding, schedule.checked)
        }
    }

    private fun checkedCard(binding:ScheduleRecyclerViewBinding, isChecked: Boolean){
        binding.apply {
            scheduleChecked.isChecked = isChecked
            scheduleItemCard.setCardBackgroundColor(Color.parseColor("#241641"))
            tvScheduleTitle.setTextColor(Color.parseColor("#828282"))
            schedulePlace.setTextColor(Color.parseColor("#828282"))
            scheduleNote.setTextColor(Color.parseColor("#828282"))
            time.setTextColor(Color.parseColor("#828282"))
            edPlace.setTextColor(Color.parseColor("#828282"))
            note.setTextColor(Color.parseColor("#828282"))
            scheduleTime.setTextColor(Color.parseColor("#828282"))
            div.setBackgroundColor(Color.parseColor("#7E64FF"))
        }
    }

    private fun uncheckedCard(binding:ScheduleRecyclerViewBinding, isChecked: Boolean){
        binding.apply {
            scheduleChecked.isChecked = isChecked
            scheduleItemCard.setCardBackgroundColor(Color.parseColor("#3C1F7B"))
            tvScheduleTitle.setTextColor(Color.parseColor("#FFFFFF"))
            schedulePlace.setTextColor(Color.parseColor("#FFFFFF"))
            scheduleNote.setTextColor(Color.parseColor("#FFFFFF"))
            time.setTextColor(Color.parseColor("#FFFFFF"))
            edPlace.setTextColor(Color.parseColor("#FFFFFF"))
            note.setTextColor(Color.parseColor("#FFFFFF"))
            scheduleTime.setTextColor(Color.parseColor("#FFFFFF"))
            div.setBackgroundColor(Color.parseColor("#C68AFF"))
        }
    }

    private fun toStringTime(date: Date): String {
        val timeFormatter = SimpleDateFormat("hh:mm a")
        return timeFormatter.format(date)
    }


}