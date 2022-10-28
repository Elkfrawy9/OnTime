package com.elkfrawy.ontime.domain.usecase.note

import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.domain.repository.NoteRepository
import com.elkfrawy.ontime.domain.usecase.UseCase
import javax.inject.Inject

class DeleteNote @Inject constructor(private val noteRepo:NoteRepository){
     suspend fun execute(data: Note) = noteRepo.delete(data)
}