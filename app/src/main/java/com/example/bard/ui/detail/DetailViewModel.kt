package com.example.bard.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.usecases.GetNoteUseCase
import com.example.bard.model.AddContent
import com.example.bard.ui.base.BaseViewModel
import com.example.bard.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase
) : BaseViewModel() {

    private val _wordList: MutableLiveData<List<AddContent>> = MutableLiveData()
    val wordList: LiveData<List<AddContent>>
        get() = _wordList

    private val _noteId: MutableLiveData<Int> = MutableLiveData()
    val noteId: LiveData<Int> = _noteId

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    fun findWordByTitle(title: String) {
        viewModelScope.launch {
            _wordList.value = getNoteUseCase.getWordsByTitle(title)
        }
    }

    fun findIdByTitle(title: String) {
        viewModelScope.launch {
            _noteId.value = getNoteUseCase.getNoteId(title).id
        }
    }

    private fun handleError(exception: Throwable) {
        val message = exception.message ?: ""
        _error.value = Event(message)
    }
}