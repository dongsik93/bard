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

    @Query("SELECT * FROM note where title = :title")
    abstract suspend fun getId(title: String): DsNoteEntity

    @Query("SELECT title FROM note")
    abstract fun getTitle(): Flow<List<String>>
}