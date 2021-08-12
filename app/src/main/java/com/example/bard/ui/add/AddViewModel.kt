package com.example.bard.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.data.AddContent
import com.example.bard.data.NoteData
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: DsRepository
) : BaseViewModel() {

    private val _noteData: MutableLiveData<Pair<String, List<AddContent>>> = MutableLiveData()
    val noteData: LiveData<Pair<String, List<AddContent>>> = _noteData

    fun saveNote(
        itemList: List<AddContent>,
        title: String,
    ) {
        viewModelScope.launch {
            repository.saveNote(itemList, title)
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            val res = repository.test(noteId)
            res.first.zip(res.second) { title, words ->
                title to words
            }.collect { _noteData.value = it }
        }
    }
}