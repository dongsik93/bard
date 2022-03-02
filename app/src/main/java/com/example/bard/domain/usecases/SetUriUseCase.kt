package com.example.bard.domain.usecases

import android.net.Uri
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.repositories.NoteRepository
import com.example.bard.presentation.utils.CsvUtils
import java.io.File
import javax.inject.Inject

class SetUriUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(uri: Uri?) = makeList(CsvUtils().readCsvData(uri))

    private suspend fun makeList(csvData: Pair<List<Array<String>>, String>): String {
        val wordList = mutableListOf<AddContent>()
        csvData.first.forEach {
            val word = it.joinToString(",").split(",")
            wordList.add(AddContent(word[0], word[1]))
        }

        repository.saveNote(
            NoteData(-1, File(csvData.second).name, wordList,)
        )

        return File(csvData.second).name
    }
}