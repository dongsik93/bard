package com.example.bard.data.repositories

import com.example.bard.data.mapper.mapWordEntityToAddContent
import com.example.bard.data.source.local.DsDataBase
import com.example.bard.data.source.local.entity.DsNoteEntity
import com.example.bard.data.source.local.entity.DsWordEntity
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.repositories.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val db: DsDataBase,
    private val ioDispatcher: CoroutineDispatcher
) : NoteRepository {

    override suspend fun saveNote(noteData: NoteData) = withContext(ioDispatcher) {
        var noteId = 0
        db.noteDao().withTransaction {
            if (noteData.noteId > 0) {
                val entity = db.noteDao().getNoteEntityById(noteData.noteId)
                db.noteDao().delete(entity)
            }
            noteId = db.noteDao().insert(DsNoteEntity.entity(noteData.title)).toInt()
            noteData.wordList.map { db.wordDao().insert(DsWordEntity.entity(noteId, it)) }
        }
        noteId
    }


    override suspend fun getNoteId(title: String): Int = withContext(ioDispatcher) {
        db.noteDao().getNoteEntityByTitle(title).id
    }

    override suspend fun getAllNoteTitle() = withContext(ioDispatcher) {
        db.noteDao().getTitle()
    }

    override suspend fun getNoteById(noteId: Int) = withContext(ioDispatcher) {
        db.noteDao().getTitleById(noteId) to mapWordEntityToAddContent(db.wordDao().getWordById(noteId))
    }

    override suspend fun getWordsByTitle(title: String) = withContext(ioDispatcher) {
        mapWordEntityToAddContent(db.wordDao().getWordById(db.noteDao().getNoteEntityByTitle(title).id))
    }
}