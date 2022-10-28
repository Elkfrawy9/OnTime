package com.elkfrawy.ontime.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.elkfrawy.ontime.R
import com.elkfrawy.ontime.databinding.ActivityMainBinding
import com.elkfrawy.ontime.presentation.note.AddEditNoteActivity
import com.elkfrawy.ontime.presentation.note.NoteFragment
import com.elkfrawy.ontime.presentation.notification.NotificationActivity
import com.elkfrawy.ontime.presentation.schedule.AddEditScheduleActivity
import com.elkfrawy.ontime.presentation.schedule.ScheduleFragment
import com.elkfrawy.ontime.presentation.util.ViewUtil.hide
import com.elkfrawy.ontime.presentation.util.ViewUtil.show
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    private val manager: FragmentManager by lazy {supportFragmentManager}

    var showFab: Boolean = false
    private var scheduleLoaded = false
    private var noteLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState == null)
            loadScheduleFragment()

        binding.buSchedule.isSelected = true

        binding.apply {
            buNote.setOnClickListener(this@MainActivity)
            buSchedule.setOnClickListener(this@MainActivity)

            addMenu.setOnClickListener {
                showFab = !showFab
                fabVisibility(showFab, it)
            }

            addNote.setOnClickListener {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                startActivity(intent)
            }

            addSchedule.setOnClickListener {
                val intent = Intent(this@MainActivity, AddEditScheduleActivity::class.java)
                startActivity(intent)
            }

            mainToolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.notification->{
                        val intent = Intent(this@MainActivity, NotificationActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.bu_note -> {
                editButton(true)
                loadNoteFragment()
            }
            else -> {
                editButton(false)
                loadScheduleFragment()
            }
        }
    }

    private fun fabVisibility(b: Boolean, v: View){
        if (b){
            binding.addNote.show()
            binding.addSchedule.show()
            binding.fabBackground.show()
            (v as FloatingActionButton).setImageResource(R.drawable.ic_cancel)
        }else {
            binding.addNote.hide()
            binding.addSchedule.hide()
            binding.fabBackground.hide()
            (v as FloatingActionButton).setImageResource(R.drawable.ic_add)
        }
    }

    private fun loadNoteFragment(){

        if (!noteLoaded) {
            manager.commit {
                replace(R.id.fragmentContainer, NoteFragment::class.java, null)
                setReorderingAllowed(true)

            }
            noteLoaded = true
        }
        scheduleLoaded = false
    }

    private fun loadScheduleFragment(){

        if (!scheduleLoaded) {
            manager.commit{
                replace(R.id.fragmentContainer, ScheduleFragment::class.java, null)
                setReorderingAllowed(true)
            }
            scheduleLoaded = true
        }
        noteLoaded = false
    }

    private fun editButton(state:Boolean){
        binding.buNote.isSelected = state
        binding.buSchedule.isSelected = !state
    }

    override fun onStart() {
        super.onStart()
        showFab = false
        fabVisibility(false, binding.addMenu)
    }
}