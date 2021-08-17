package com.example.bard.domain.usecases

import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class GetWordsByTitleUseCase @Inject constructor(private val repository: NoteRepository) {
    /* 단어장 제목으로 해당 단어장 내용 가져오기 */
    suspend operator fun invoke(title: String) = repository.getWordsByTitle(title)
}