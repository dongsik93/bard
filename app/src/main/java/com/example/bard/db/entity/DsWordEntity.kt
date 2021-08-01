package com.example.bard.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/**
 * DsWordEntity
 * @desc 단어 table
 */
@Entity(
    tableName = "word",
    inheritSuperIndices = true,
    foreignKeys = [ForeignKey(
        entity = DsNoteEntity::class,
        parentColumns = ["id"],
        childColumns = ["note_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DsWordEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "note_id")
    var noteId: Int,

    @ColumnInfo(name = "word")
    var word: String,
)
