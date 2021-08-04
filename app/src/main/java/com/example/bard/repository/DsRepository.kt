package com.example.bard.repository

import com.example.bard.data.AddContent
import com.example.bard.db.DsDataBase
import com.example.bard.db.entity.DsNoteEntity
import com.example.bard.db.entity.DsWordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DsRepository private constructor(
    private val db: DsDataBase
) {

    private val ioDispatcher = Dispatchers.IO

    suspend fun saveNote(
        itemList: List<AddContent>,
        title: String,
    ) {
        db.noteDao().withTransaction {
            db.noteDao().insert(DsNoteEntity.entity(title))
            val noteData = getNoteId(title)
            itemList.map { db.wordDao().insert(DsWordEntity.entity(noteData.id, it)) }
        }
    }

    private suspend fun getNoteId(title: String) = withContext(ioDispatcher) {
        db.noteDao().getId(title)
    }

    /* Flow */
    fun loadNoteTitle(): Flow<List<String>> = db.noteDao().getTitle()

//    /* suspend fun */
//    suspend fun loadNoteTitle2(): List<String> = withContext(ioDispatcher) {
//        db.noteDao().getId()
//    }

    suspend fun findWordWithTitle(title: String): Flow<List<AddContent>> {
        val noteId = db.noteDao().getId(title)
        return db.wordDao().getWordById(noteId.id).map { makeAddContent(it) }
    }

    private fun makeAddContent(data: List<DsWordEntity>) =
        mutableListOf<AddContent>().also { data.map { AddContent(it.word, it.meaning) } }


    companion object {
        @Volatile
        private var INSTANCE: DsRepository? = null

        fun getInstance(database: DsDataBase): DsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DsRepository(database).also { INSTANCE = it }
            }
    }
}