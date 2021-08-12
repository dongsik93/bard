package com.example.bard.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.data.AddContent
import com.example.bard.data.NoteData
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: DsRepository
) : BaseViewModel() {

    private val _noteData: MutableLiveData<NoteData> = MutableLiveData()
    val noteData: LiveData<NoteData> = _noteData

    fun saveNote(
        noteItem: NoteData,
    ) {
        viewModelScope.launch {
            repository.saveNote(noteItem)
        }
    }

    fun findNoteById(noteId: Int) {
        println(">>>>>>>>> 호출 >>>> ")
        viewModelScope.launch {
            val res = repository.test(noteId)
            res.first.zip(res.second) { title, words ->
                println(">>>>>>>>>> title : $title, words : $words")
                NoteData(
                    noteId,
                    title,
                    words.toMutableList()
                )
            }.catch { println(">>>>>>>>>>>>> $it") }.collect { _noteData.value = it }
        }
    }
}