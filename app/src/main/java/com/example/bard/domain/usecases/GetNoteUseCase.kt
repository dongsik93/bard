package com.example.bard.domain.usecases

import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    /* 단어장 id 가져오기 */
    suspend fun getNoteId(title: String) = repository.getNoteId(title)

    /* 단어장 제목 리스트 가져오기 */
    suspend fun getAllNoteTitle() = repository.getAllNoteTitle()

    /* 단어장 id로 단어장 제목, 내용 가져오기 */
    suspend fun getNoteById(noteId: Int) = repository.getNoteById(noteId)

    /* 단어장 제목으로 해당 단어장 내용 가져오기 */
    suspend fun getWordsByTitle(title: String) = repository.getWordsByTitle(title)
}