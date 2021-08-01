package com.example.bard.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * DsNoteEntity
 * @desc 단어장 table
 */
@Entity(tableName = "note")
data class DsNoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,
) {
    companion object {
    }
}