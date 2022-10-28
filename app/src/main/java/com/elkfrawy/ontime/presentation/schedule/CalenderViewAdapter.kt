package com.elkfrawy.ontime.presentation.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.R
import com.elkfrawy.ontime.databinding.CalendarCellBinding
import com.elkfrawy.ontime.domain.model.Dates
import java.util.*
import kotlin.collections.ArrayList

class CalenderViewAdapter(var days: List<String> = ArrayList<String>()) :
    RecyclerView.Adapter<CalenderViewAdapter.ViewHolder>() {

    lateinit var mListener: OnItemClicked
    var dates: List<Dates> = ArrayList()
    var mYear:Int? = null
    var mMonth:Int? = null
    var pos = 0
    val calendar by lazy {
       Calendar.getInstance()
    }

    class ViewHolder(val binding:CalendarCellBinding, listener: OnItemClicked) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.calendarDay.setOnClickListener{ listener.onItemClickListener(adapterPosition, (it as TextView).text.toString()) }
        }
    }

    fun getYearMonth(year:Int, month:Int){
        mYear = year
        mMonth = month
    }

    fun getDays(days: List<String>){
        this.days = days
        notifyDataSetChanged()
        pos = 0
    }

    fun markText(date: List<Dates>){
        dates = date
        dates = dates.filter {
            val cal = Calendar.getInstance()
            cal .time = it.date
            !(cal.get(Calendar.MONTH) + 1 != mMonth || cal.get(Calendar.YEAR)  != mYear)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CalendarCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val parms:ViewGroup.LayoutParams = binding.root.layoutParams
        parms.height = (parent.height * 0.166666666).toInt()

        return ViewHolder(binding, mListener)

    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val day = days[position]
        holder.binding.calendarDay.text = day
        if (day.isNotBlank() && dates.isNotEmpty()){
            if (pos < dates.size){
                if (checkDate(dates[pos].date, day.toInt())) {
                   holder.binding.calendarDay.setBackgroundResource(R.drawable.mark_text)
                    pos++
                }
            }
        }

        if (day.isNotBlank()){
            if (isToday(calendar, day.toInt(), mMonth!!, mYear!!))
                holder.binding.calendarDay.setBackgroundResource(R.drawable.today_text)
        }
    }

    private fun isToday(calendar: Calendar, day:Int, month: Int, year: Int) : Boolean{

        if (calendar.get(Calendar.DAY_OF_MONTH) != day) return false
        if (calendar.get(Calendar.MONTH) + 1 != month) return false
        if (calendar.get(Calendar.YEAR) != year) return false

        return true
    }

    private fun checkDate(date: Date, day:Int) : Boolean{
        val cal = Calendar.getInstance()
        cal.time = date
        if (cal.get(Calendar.DAY_OF_MONTH) != day) return false
        return true
    }

    fun onItemClick(listener: OnItemClicked){
        mListener = listener
    }

    interface OnItemClicked{
        fun onItemClickListener(position:Int, day:String)
    }
}