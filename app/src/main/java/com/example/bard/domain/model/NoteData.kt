package com.example.bard.domain.model

data class NoteData(
    val noteId: Int,
    var title: String,
    var wordList: List<AddContent>,
)
