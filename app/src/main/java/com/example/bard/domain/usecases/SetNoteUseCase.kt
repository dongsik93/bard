package com.example.bard.domain.usecases

import com.example.bard.domain.repositories.NoteRepository
import com.example.bard.domain.model.NoteData
import javax.inject.Inject

class SetNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend fun saveNote(noteData: NoteData) = repository.saveNote(noteData)
}