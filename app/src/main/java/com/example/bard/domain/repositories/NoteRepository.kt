package com.example.bard.domain.repositories

import com.example.bard.data.source.local.entity.DsNoteEntity
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.model.NoteData

interface NoteRepository {
    /* 단어장 저장 */
    suspend fun saveNote(noteData: NoteData)

    /* 단어장 id 가져오기 */
    suspend fun getNoteId(title: String): DsNoteEntity

    /* 단어장 제목 리스트 가져오기 */
    suspend fun getAllNoteTitle(): List<String>

    /* 단어장 id로 단어장 제목, 내용 가져오기 */
    suspend fun getNoteById(noteId: Int): Pair<String, List<AddContent>>

    /* 단어장 제목으로 해당 단어장 내용 가져오기 */
    suspend fun getWordsByTitle(title: String): List<AddContent>
}