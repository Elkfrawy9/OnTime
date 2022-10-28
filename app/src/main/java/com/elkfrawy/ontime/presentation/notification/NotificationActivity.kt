package com.elkfrawy.ontime.presentation.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.R
import com.elkfrawy.ontime.databinding.ActivityNotificationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding
    val viewModel:NotificationViewModel by viewModels()
    val adapter:NotificationAdapter by lazy { NotificationAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.notificationRecyclerView.adapter = adapter

        viewModel.notifications.observe(this){
            adapter.getNotification(it)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.executeDelete(adapter.mNotification.get(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(binding.notificationRecyclerView)

        binding.notificationToolbar.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.deleteAll ->{
                    showConfirmation()
                    true
                } else -> false
            }
        }

        binding.notificationToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun showConfirmation(){
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Delete All")
            .setMessage("Are you sure you want to delete all notification")
            .setNegativeButton("No"){ p0, _ ->
                p0.dismiss()
            }.setPositiveButton("Yes"){p0, _ ->
                viewModel.executeDeleteAll(adapter.mNotification)
                p0.dismiss()
            }

        dialog.show()
    }
}