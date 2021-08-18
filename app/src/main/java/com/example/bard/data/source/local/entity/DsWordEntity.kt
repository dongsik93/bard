package com.example.bard.data.source.local.entity

import androidx.room.*
import com.example.bard.domain.model.AddContent

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
    )],
    indices = [Index(value = ["note_id"])]
)
data class DsWordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "note_id")
    var noteId: Int,

    @ColumnInfo(name = "word")
    var word: String,

    @ColumnInfo(name = "meaning")
    var meaning: String,
) {
    companion object {
        fun entity(noteId: Int, item: AddContent): DsWordEntity {
            return DsWordEntity(
                noteId = noteId,
                word = item.word,
                meaning = item.meaning,
            )
        }

        fun makeAddContent(data: List<DsWordEntity>): MutableList<AddContent> {
            return mutableListOf<AddContent>().also { _list ->
                data.forEach { entity ->
                    _list.add(AddContent(entity.word, entity.meaning))
                }
            }
        }
    }
}
