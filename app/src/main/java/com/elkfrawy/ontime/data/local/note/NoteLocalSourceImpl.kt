package com.elkfrawy.ontime.data.local.note

import com.elkfrawy.ontime.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteLocalSourceImpl @Inject constructor(private val dao: NoteDAO) : NoteLocalSource {

    override suspend fun insert(note: Note) = withContext(Dispatchers.IO) {
        dao.insert(note)
    }

    override suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        dao.update(note)
    }

    override suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        dao.delete(note)
    }

    override suspend fun searchNote(text: String): Flow<List<Note>> = withContext(Dispatchers.IO) {
        dao.getNotes(text)
    }
}