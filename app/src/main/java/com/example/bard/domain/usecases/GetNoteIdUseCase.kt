package com.example.bard.domain.usecases

import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class GetNoteIdUseCase @Inject constructor(private val repository: NoteRepository) {
    /* 단어장 id 가져오기 */
    suspend operator fun invoke(title: String) = repository.getNoteId(title)
}