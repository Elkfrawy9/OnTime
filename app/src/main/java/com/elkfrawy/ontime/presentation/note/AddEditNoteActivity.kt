package com.elkfrawy.ontime.presentation.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.elkfrawy.ontime.R
import com.elkfrawy.ontime.data.constant.EDIT_NOTE_KEY
import com.elkfrawy.ontime.data.constant.EDIT_NOTE_VALUE
import com.elkfrawy.ontime.data.constant.EXTRA_NOTE
import com.elkfrawy.ontime.databinding.ActivityAddEditNoteBinding
import com.elkfrawy.ontime.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddEditNoteActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    val viewModel:NoteViewModel by viewModels()
    lateinit var binding:ActivityAddEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        viewModel.noteStatus = intent.getIntExtra(EDIT_NOTE_KEY, 0)

        if (viewModel.noteStatus == EDIT_NOTE_VALUE)
            putNoteData(intent)

        binding.addNoteToolbar.setOnMenuItemClickListener(this)

        binding.addNoteToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun putNoteData(intent: Intent){
        val note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        viewModel.note = note!!
        viewModel.pin = note.pin
        pinUpdate()
        setNoteData(note.title, note.text)
    }

    private fun pinUpdate(){
        val pinView= binding.addNoteToolbar.menu.findItem(R.id.pin)
        if (viewModel.pin)
            pinView.setIcon(R.drawable.ic_pin)
        else
            pinView.setIcon(R.drawable.ic_unpin)
    }

    private fun setNoteData(title:String, text:String){
        binding.edTitle.setText(title)
        binding.edText.setText(text)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.save-> {
                val title = binding.edTitle.text.toString()
                val text = binding.edText.text.toString()
                if (title.isBlank() && text.isBlank())
                    Toast.makeText(this@AddEditNoteActivity, "Empty note", Toast.LENGTH_LONG).show()

                else {
                    val cal = Calendar.getInstance()
                    if (viewModel.noteStatus == EDIT_NOTE_VALUE)
                        updateNote(title, text, cal)
                    else
                        insertNote(title, text, cal)
                    onBackPressed()
                }
                return true
            }

            R.id.pin -> {
                viewModel.pin = !viewModel.pin
                pinUpdate()
                return true
            }
            else -> return false
        }
    }

    private fun updateNote(title: String, text: String, cal: Calendar){
        viewModel.note?.let {
            val newNote = Note(it.id, title, text, cal.time, viewModel.pin)
            viewModel.executeUpdate(newNote)
        }
    }

    private fun insertNote(title: String, text: String, cal: Calendar){
        val note = Note(
            title = title,
            text = text,
            pin = viewModel.pin,
            date = cal.time)

        viewModel.executeInsert(note)
    }
}