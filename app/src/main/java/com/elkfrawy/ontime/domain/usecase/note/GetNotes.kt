package com.elkfrawy.ontime.domain.usecase.note

import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.repository.NoteRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotes @Inject constructor(private val noteRepo: NoteRepository)  {
     suspend fun execute(data: String): Flow<List<Note>> = noteRepo.searchNote(data)
}