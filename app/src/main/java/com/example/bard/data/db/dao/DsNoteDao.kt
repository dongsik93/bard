package com.example.bard.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.bard.data.db.entity.DsNoteEntity

@Dao
abstract class DsNoteDao : BaseDao<DsNoteEntity> {
    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    @Query("SELECT * FROM note WHERE title = :title")
    abstract suspend fun getNoteEntityByTitle(title: String): DsNoteEntity

    @Query("SELECT * FROM note WHERE id = :noteId")
    abstract suspend fun getNoteEntityById(noteId: Int): DsNoteEntity

    @Query("SELECT title FROM note")
    abstract suspend fun getTitle(): List<String>

    @Query("SELECT title FROM note WHERE id = :noteId")
    abstract suspend fun getTitleById(noteId: Int): String
}