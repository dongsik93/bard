package com.example.bard.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.usecases.GetNoteUseCase
import com.example.bard.domain.usecases.SetNoteUseCase
import com.example.bard.model.NoteData
import com.example.bard.ui.base.BaseViewModel
import com.example.bard.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val setNoteUseCase: SetNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
) : BaseViewModel() {

    init {
        loadNoteList()
    }

    private val _noteList: MutableLiveData<List<String>> = MutableLiveData()
    val noteList: LiveData<List<String>>
        get() = _noteList

    private val _error: MutableLiveData<Event<String>> = MutableLiveData()
    val error: LiveData<Event<String>> = _error

    private fun loadNoteList() {
        viewModelScope.launch {
            _noteList.value = getNoteUseCase.getAllNoteTitle()
        }
    }

    private fun handleError(exception: Throwable) {
        val message = exception.message ?: ""
        _error.value = Event(message)
    }

    fun saveNote(
        noteData: NoteData
    ) {
        viewModelScope.launch {
            setNoteUseCase.saveNote(noteData)
        }
    }
}