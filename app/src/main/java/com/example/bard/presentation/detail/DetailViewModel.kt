package com.example.bard.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.usecases.GetNoteByIdUseCase
import com.example.bard.domain.usecases.GetNoteIdUseCase
import com.example.bard.domain.usecases.GetWordsByTitleUseCase
import com.example.bard.presentation.base.BaseViewModel
import com.example.bard.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteIdUseCase,
    private val getWordsByTitleUseCase: GetWordsByTitleUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
) : BaseViewModel() {

    private val _wordList: MutableLiveData<NoteData> = MutableLiveData()
    val wordList: LiveData<NoteData>
        get() = _wordList

    private val _noteId: MutableLiveData<Int> = MutableLiveData()
    val noteId: LiveData<Int> = _noteId

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    fun findWordByTitle(title: String) {
        viewModelScope.launch {
            _wordList.value = getWordsByTitleUseCase(title)
        }
    }

    fun findIdByTitle(title: String) {
        viewModelScope.launch {
            _noteId.value = getNoteUseCase(title).id
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            val res = getNoteByIdUseCase(noteId)
            _wordList.value = NoteData(noteId, res.first, res.second)
        }
    }

    private fun handleError(exception: Throwable) {
        val message = exception.message ?: ""
        _error.value = Event(message)
    }
}