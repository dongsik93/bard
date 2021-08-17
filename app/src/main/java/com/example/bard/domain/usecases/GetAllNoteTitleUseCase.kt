package com.example.bard.domain.usecases

import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class GetAllNoteTitleUseCase @Inject constructor(private val repository: NoteRepository) {
    /* 단어장 제목 리스트 가져오기 */
    suspend operator fun invoke() = repository.getAllNoteTitle()
}