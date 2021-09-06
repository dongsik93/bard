package com.example.bard.presentation.note

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.usecases.GetAllNoteTitleUseCase
import com.example.bard.domain.usecases.SetUriUseCase
import com.example.bard.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val setUriUseCase: SetUriUseCase,
    private val getAllNoteTitleUseCase: GetAllNoteTitleUseCase,
) : BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadNoteList()
    }

    fun loadNoteList() {
        viewModelScope.launch {
            event(Event.NoteListTitle(getAllNoteTitleUseCase()))
        }
    }

    fun saveUri(uri: Uri?) {
        viewModelScope.launch {
            event(Event.CsvTitle(setUriUseCase(uri)))
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class NoteListTitle(val noteTitles: List<String>) : Event()
        data class CsvTitle(val csvTitle: String) : Event()
        data class ShowToast(val text: String) : Event()
    }
}