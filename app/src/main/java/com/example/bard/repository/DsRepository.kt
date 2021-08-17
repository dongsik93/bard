package com.example.bard.repository

import com.example.bard.model.AddContent
import com.example.bard.model.NoteData
import com.example.bard.data.db.DsDataBase
import com.example.bard.data.db.entity.DsNoteEntity
import com.example.bard.data.db.entity.DsWordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DsRepository @Inject constructor(
    private val db: DsDataBase,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun saveNote(
        noteData: NoteData
    ) {
        withContext(ioDispatcher) {
            db.noteDao().withTransaction {
                if (noteData.noteId > 0) {
                    val entity = db.noteDao().getNoteEntityById(noteData.noteId)
                    db.noteDao().delete(entity)
                }
                db.noteDao().insert(DsNoteEntity.entity(noteData.title))
                val data = getNoteId(noteData.title)
                noteData.wordList.map { db.wordDao().insert(DsWordEntity.entity(data.id, it)) }
            }
        }
    }

    suspend fun getNoteId(title: String) = withContext(ioDispatcher) {
        db.noteDao().getNoteEntityByTitle(title)
    }

    suspend fun loadNoteTitle(): List<String> = withContext(ioDispatcher) {
        db.noteDao().getTitle()
    }

    suspend fun test(noteId: Int): Pair<String, List<AddContent>> = withContext(ioDispatcher) {
        val title = db.noteDao().getTitleById(noteId)
        val noteData = makeAddContent(db.wordDao().getWordById(noteId))
        title to noteData
    }

    suspend fun findWordWithTitle(title: String): List<AddContent> = withContext(ioDispatcher) {
        val noteId = db.noteDao().getNoteEntityByTitle(title)
        makeAddContent(db.wordDao().getWordById(noteId.id))
    }

    private fun makeAddContent(data: List<DsWordEntity>) = data.map { AddContent(it.word, it.meaning) }

    companion object {
        @Volatile
        private var INSTANCE: DsRepository? = null

        fun getInstance(database: DsDataBase, dispatcher: CoroutineDispatcher): DsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DsRepository(database, dispatcher).also { INSTANCE = it }
            }
    }
}