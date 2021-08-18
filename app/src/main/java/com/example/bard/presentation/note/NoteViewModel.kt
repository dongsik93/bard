package com.example.bard.presentation.note

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.usecases.GetAllNoteTitleUseCase
import com.example.bard.domain.usecases.SetUriUseCase
import com.example.bard.presentation.base.BaseViewModel
import com.example.bard.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val setUriUseCase: SetUriUseCase,
    private val getAllNoteTitleUseCase: GetAllNoteTitleUseCase,
) : BaseViewModel() {

    private val _noteList: MutableLiveData<List<String>> = MutableLiveData()
    val noteList: LiveData<List<String>>
        get() = _noteList

    private val _csvTitle: MutableLiveData<String> = MutableLiveData()
    val csvTitle: LiveData<String> = _csvTitle

    private val _error: MutableLiveData<Event<String>> = MutableLiveData()
    val error: LiveData<Event<String>> = _error

    init {
        loadNoteList()
    }

    fun loadNoteList() {
        viewModelScope.launch {
            _noteList.value = getAllNoteTitleUseCase()
        }
    }

    fun saveUri(uri: Uri?) {
        viewModelScope.launch {
            _csvTitle.value = setUriUseCase(uri)
        }
    }

    private fun handleError(exception: Throwable) {
        val message = exception.message ?: ""
        _error.value = Event(message)
    }
}