package com.example.bard.repository

import com.example.bard.data.AddContent
import com.example.bard.data.NoteData
import com.example.bard.db.DsDataBase
import com.example.bard.db.entity.DsNoteEntity
import com.example.bard.db.entity.DsWordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DsRepository @Inject constructor(
    private val db: DsDataBase,
    private val ioDispatcher: CoroutineDispatcher
) {

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

    suspend fun getNoteId(title: String) = withContext(ioDispatcher) {
        db.noteDao().getId(title)
    }

    /* Flow */
    fun loadNoteTitle(): Flow<List<String>> = db.noteDao().getTitle()

//    /* suspend fun */
//    suspend fun loadNoteTitle2(): List<String> = withContext(ioDispatcher) {
//        db.noteDao().getId()
//    }

    fun test(noteId: Int): Pair<Flow<String>, Flow<List<AddContent>>> {
        val title = db.noteDao().getTitleById(noteId)
        val noteData = db.wordDao().getWordById(noteId).map { makeAddContent(it) }
        return title to noteData
    }

    suspend fun findWordWithTitle(title: String): Flow<List<AddContent>> {
        val noteId = db.noteDao().getId(title)
        return db.wordDao().getWordById(noteId.id).map { makeAddContent(it) }
    }

    private fun makeAddContent(data: List<DsWordEntity>) = data.map { AddContent(it.word, it.meaning) }

    fun findNoteById(noteId: Int): Flow<NoteData> {
        return flow {
            
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DsRepository? = null

        fun getInstance(database: DsDataBase, dispatcher: CoroutineDispatcher): DsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DsRepository(database, dispatcher).also { INSTANCE = it }
            }
    }
}