package com.example.bard.data.repositories

import com.example.bard.data.db.DsDataBase
import com.example.bard.data.db.entity.DsNoteEntity
import com.example.bard.data.db.entity.DsWordEntity
import com.example.bard.domain.repositories.NoteRepository
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.model.NoteData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val db: DsDataBase,
    private val ioDispatcher: CoroutineDispatcher
) : NoteRepository {

    override suspend fun saveNote(noteData: NoteData) {
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

    override suspend fun getNoteId(title: String) = withContext(ioDispatcher) {
        db.noteDao().getNoteEntityByTitle(title)
    }

    override suspend fun getAllNoteTitle() = withContext(ioDispatcher) {
        println(">>>>>>>>>>>>> 통과 >>>>>>>>> ")
        db.noteDao().getTitle()
    }

    override suspend fun getNoteById(noteId: Int) = withContext(ioDispatcher) {
        val title = db.noteDao().getTitleById(noteId)
        val noteData = makeAddContent(db.wordDao().getWordById(noteId))
        title to noteData
    }

    override suspend fun getWordsByTitle(title: String) = withContext(ioDispatcher) {
        val noteId = db.noteDao().getNoteEntityByTitle(title)
        makeAddContent(db.wordDao().getWordById(noteId.id))
    }

    private fun makeAddContent(data: List<DsWordEntity>) = data.map { AddContent(it.word, it.meaning) }
}