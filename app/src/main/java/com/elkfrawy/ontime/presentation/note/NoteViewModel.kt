package com.elkfrawy.ontime.presentation.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.usecase.note.DeleteNote
import com.elkfrawy.ontime.domain.usecase.note.GetNotes
import com.elkfrawy.ontime.domain.usecase.note.InsertNote
import com.elkfrawy.ontime.domain.usecase.note.UpdateNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val updateNote: UpdateNote,
    private val insertNote: InsertNote,
    private val deleteNote: DeleteNote,
    private val getNotes: GetNotes
) : ViewModel() {

    var pin:Boolean = false
    var note: Note?= null
    var noteStatus:Int = 0

    fun executeInsert(note: Note) {
        viewModelScope.launch {
            insertNote.execute(note)
        }
    }

    fun executeDelete(note: Note) {
        viewModelScope.launch {
            deleteNote.execute(note)
        }
    }

    fun executeUpdate(note: Note) {
        viewModelScope.launch {
            updateNote.execute(note)
        }
    }

    val searchFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val notes = searchFlow.flatMapLatest {
        withContext(viewModelScope.coroutineContext) {
            getNotes.execute(it)
        }
    }

    val notesLiveData: LiveData<List<Note>> = notes.asLiveData()

}