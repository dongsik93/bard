package com.example.bard.repository

import com.example.bard.data.AddContent
import com.example.bard.db.DsDataBase
import com.example.bard.db.entity.DsNoteEntity
import com.example.bard.db.entity.DsWordEntity

class DsRepository private constructor(
    private val db: DsDataBase
) {

    suspend fun saveNote(
        itemList: List<AddContent>,
        title: String,
    ) {
        db.noteDao().withTransaction {
            db.noteDao().insert(DsNoteEntity.entity(title))
            val noteData = db.noteDao().getId(title)
            itemList.map { db.wordDao().insert(DsWordEntity.entity(noteData.id, it)) }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: DsRepository? = null

        fun getInstance(database: DsDataBase): DsRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DsRepository(database).also { INSTANCE = it }
            }
    }
}