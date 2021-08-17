package com.example.bard.domain.usecases

import com.example.bard.domain.model.NoteData
import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class SetNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(noteData: NoteData, ) {
        repository.saveNote(noteData)
    }
}