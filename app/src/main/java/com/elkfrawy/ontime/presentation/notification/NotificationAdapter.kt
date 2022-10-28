package com.elkfrawy.ontime.presentation.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.databinding.NotificationItemBinding
import com.elkfrawy.ontime.domain.model.Notification

class NotificationAdapter() : RecyclerView.Adapter<NotificationAdapter.Holder>() {

    var mNotification:List<Notification> = listOf()

    class Holder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root){}

    fun getNotification(notifications:List<Notification>){
        mNotification = notifications
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val notification = mNotification.get(position)
        holder.binding.apply {
            notificationTitle.text =  notification.title
            notificationDate.text =  notification.date
        }
    }

    override fun getItemCount() = mNotification.size

}