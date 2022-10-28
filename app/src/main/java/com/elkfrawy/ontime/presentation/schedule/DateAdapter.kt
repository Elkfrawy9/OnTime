package com.elkfrawy.ontime.presentation.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.databinding.ScheduleViewBinding
import com.elkfrawy.ontime.domain.model.DateWithSchedule
import java.text.SimpleDateFormat
import java.util.*


class DateAdapter constructor(
    val viewModel: ScheduleViewModel
) : ListAdapter<DateWithSchedule, DateAdapter.ViewHolder>(DiffCallBack())  {

    lateinit var mAdapter:ScheduleAdapter
    var lastData:Date = Date(0)

    inner class ViewHolder(val binding:ScheduleViewBinding):RecyclerView.ViewHolder(binding.root){}

    private class DiffCallBack : DiffUtil.ItemCallback<DateWithSchedule>(){
        override fun areItemsTheSame(oldItem: DateWithSchedule, newItem: DateWithSchedule): Boolean {
            return oldItem.dates == newItem.dates
        }
        override fun areContentsTheSame(oldItem: DateWithSchedule, newItem: DateWithSchedule): Boolean {
            return oldItem.Schedules == newItem.Schedules
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mAdapter = ScheduleAdapter(viewModel)
        val binding =
            ScheduleViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = currentList[position]
        val day = getDay(data.dates.date)

            holder.binding.scheduleDateDay.text = day
            holder.binding.scheduleItemRecyclerView.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
            }
        mAdapter.submitList(data.Schedules)
    }

    private fun getDay(date: Date): String{
        val formatter = SimpleDateFormat("dd")
        return formatter.format(date)
    }


}