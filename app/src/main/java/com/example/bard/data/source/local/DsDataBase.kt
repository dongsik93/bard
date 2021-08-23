package com.example.bard.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bard.data.source.local.dao.DsNoteDao
import com.example.bard.data.source.local.dao.DsWordDao
import com.example.bard.data.source.local.entity.DsNoteEntity
import com.example.bard.data.source.local.entity.DsWordEntity

@Database(
    entities = [
        DsNoteEntity::class,
        DsWordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DsDataBase : RoomDatabase() {
    abstract fun noteDao(): DsNoteDao
    abstract fun wordDao(): DsWordDao
}