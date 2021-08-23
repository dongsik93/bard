package com.example.bard.domain.usecases

import com.example.bard.data.source.local.entity.DsWordEntity
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repository: NoteRepository) {
    /* 단어장 id로 단어장 제목, 내용 가져오기 */
    suspend operator fun invoke(noteId: Int): Pair<String, List<AddContent>> {
        val res = repository.getNoteById(noteId)
        return res.first to DsWordEntity.makeAddContent(res.second)
    }
}