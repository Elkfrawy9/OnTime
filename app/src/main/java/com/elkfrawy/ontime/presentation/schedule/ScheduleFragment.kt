package com.elkfrawy.ontime.presentation.schedule

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.databinding.FragmentScheduleBinding
import com.elkfrawy.ontime.databinding.ScheduleViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.time.Clock
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ScheduleFragment : Fragment(), CalenderViewAdapter.OnItemClicked {

    lateinit var localDate: LocalDate
    lateinit var CalenderAdapter: CalenderViewAdapter
    lateinit var dateAdapter: DateAdapter
    lateinit var scheduleItemBinding: ScheduleViewBinding
    val viewModel:ScheduleViewModel by viewModels()
    lateinit var binding: FragmentScheduleBinding
    lateinit var scheduleAdapter:ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        scheduleItemBinding = ScheduleViewBinding.inflate(layoutInflater)
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        scheduleAdapter = ScheduleAdapter(viewModel)
        dateAdapter = DateAdapter(viewModel)
        CalenderAdapter = CalenderViewAdapter()

        localDate = LocalDate.now()
        binding.calendarRecyclerView.apply {
            adapter = CalenderAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

        binding.scheduleRecyclerView.apply {
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        viewModel.dateWithSchedule.observe(viewLifecycleOwner){
            val data = it.distinctBy {it.dates.date.time}
            Log.d("Elkfrawy9", "dateWithSchedule: $data")
            CalenderAdapter.getDays(getDaysList())
            dateAdapter.submitList(data)
            binding.scheduleProgress.visibility = View.GONE

        }

        CalenderAdapter.getDays(getDaysList())

        updateDate()
        binding.previousMonth.setOnClickListener{
            localDate = localDate.minusMonths(1)
            Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            CalenderAdapter.getDays(getDaysList())
            updateDate()
        }

        binding.forwardMonth.setOnClickListener{
            localDate = localDate.plusMonths(1)
            CalenderAdapter.getDays(getDaysList())
            updateDate()
        }

        CalenderAdapter.onItemClick(this)
    }

    @SuppressLint("NewApi")
    private fun updateDate(){
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        binding.dateText.text = localDate.format(formatter)
    }


    @SuppressLint("NewApi")
    fun getDaysList():MutableList<String>{

       runBlocking(Dispatchers.IO){
            viewModel.dateList = viewModel.getDates().distinctBy { it.date.time}
       }

        val list:MutableList<String> = ArrayList<String>()

        val yearMonth: YearMonth = YearMonth.from(localDate)
        val days = yearMonth.lengthOfMonth()

        val firstOfMonth: LocalDate = localDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42){
            if (i <= dayOfWeek || i > days + dayOfWeek )
                list.add("")
            else
                list.add((i - dayOfWeek).toString())
        }
        CalenderAdapter.getYearMonth(yearMonth.year, yearMonth.monthValue)
        CalenderAdapter.markText(viewModel.dateList)
        return list
    }

    override fun onItemClickListener(position: Int, day: String) {
    }
}