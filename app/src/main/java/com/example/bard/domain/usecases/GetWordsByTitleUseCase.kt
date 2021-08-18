package com.example.bard.domain.usecases

import com.example.bard.data.source.local.entity.DsWordEntity
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.repositories.NoteRepository
import javax.inject.Inject

class GetWordsByTitleUseCase @Inject constructor(private val repository: NoteRepository) {
    /* 단어장 제목으로 해당 단어장 내용 가져오기 */
    suspend operator fun invoke(title: String): NoteData {
        return NoteData(
            repository.getNoteId(title).id,
            title ,
            makeAddContent(repository.getWordsByTitle(title)),
        )
    }

    private fun makeAddContent(data: List<DsWordEntity>): MutableList<AddContent> {
        return mutableListOf<AddContent>().also { _list ->
            data.forEach {
                _list.add(AddContent(it.word, it.meaning))
            }
        }
    }
}