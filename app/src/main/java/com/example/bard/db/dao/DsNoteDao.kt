package com.example.bard.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.bard.db.entity.DsNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DsNoteDao : BaseDao<DsNoteEntity> {
    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

    @Query("SELECT * FROM note WHERE title = :title")
    abstract suspend fun getNoteEntityByTitle(title: String): DsNoteEntity

    @Query("SELECT * FROM note WHERE id = :noteId")
    abstract suspend fun getNoteEntityById(noteId: Int): DsNoteEntity

    @Query("SELECT title FROM note")
    abstract fun getTitle(): Flow<List<String>>

    @Query("SELECT title FROM note WHERE id = :noteId")
    abstract fun getTitleById(noteId: Int): Flow<String>
}