package com.example.bard.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bard.data.db.dao.DsNoteDao
import com.example.bard.data.db.dao.DsWordDao
import com.example.bard.data.db.entity.DsNoteEntity
import com.example.bard.data.db.entity.DsWordEntity

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

    companion object {
        @Volatile
        private var INSTANCE: DsDataBase? = null
        private const val DATABASE_NAME = "bard-data.db"

        fun getInstance(context: Context): DsDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            DsDataBase::class.java,
            DATABASE_NAME
        )
//            .addMigrations(MIGRATION_3_TO_4)
            .fallbackToDestructiveMigration()
            .build()
    }
}