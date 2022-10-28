package com.elkfrawy.ontime.data.repository

import com.elkfrawy.ontime.data.local.note.NoteLocalSource
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteSource: NoteLocalSource) : NoteRepository{

    override suspend fun insert(note: Note) = noteSource.insert(note)
    override suspend fun update(note: Note) = noteSource.update(note)
    override suspend fun delete(note: Note) = noteSource.delete(note)
    override suspend fun searchNote(text: String): Flow<List<Note>> = noteSource.searchNote(text)

}