package com.example.bard.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.data.NoteData
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: DsRepository
) : BaseViewModel() {

    private val _noteData: MutableLiveData<NoteData> = MutableLiveData()
    val noteData: LiveData<NoteData> = _noteData

    fun saveNote(noteItem: NoteData, ) {
        viewModelScope.launch {
            repository.saveNote(noteItem)
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            val res = repository.test(noteId)
            _noteData.value = NoteData(
                noteId,
                res.first,
                res.second.toMutableList(),
            )
        }
    }
}