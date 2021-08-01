package com.example.bard.data

data class AddContent(
    val word: String,
    val meaning: String,
) {
    companion object {
        fun default() = AddContent("", "")
    }
}
