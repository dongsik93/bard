package com.example.bard.data

data class NoteData(
    val noteId: Int,
    var title: String,
    var wordList: MutableList<AddContent>,
)
