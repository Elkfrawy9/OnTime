package com.elkfrawy.ontime.domain.repository

import com.elkfrawy.ontime.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(note: Note)
    suspend fun searchNote(text:String): Flow<List<Note>>




}