package com.example.bard.model

data class NoteData(
    val noteId: Int,
    var title: String,
    var wordList: MutableList<AddContent>,
)
