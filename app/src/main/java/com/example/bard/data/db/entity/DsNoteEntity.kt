package com.example.bard.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * DsNoteEntity
 * @desc 단어장 table
 */
@Entity(tableName = "note", indices = [Index(value = ["title"], unique = true)])
data class DsNoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String,
) {
    companion object {
        fun entity(title: String) = DsNoteEntity(title = title)
    }
}