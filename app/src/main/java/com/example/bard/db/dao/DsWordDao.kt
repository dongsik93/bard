package com.example.bard.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bard.db.entity.DsWordEntity

@Dao
abstract class DsWordDao : BaseDao<DsWordEntity> {
    @Query("SELECT * FROM word WHERE note_id = :noteId")
    abstract fun getWordById(noteId: Int): List<DsWordEntity>
}