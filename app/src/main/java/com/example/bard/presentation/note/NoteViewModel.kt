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

    private val _eventFlow = MutableSharedFlow<NoteEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadNoteList()
    }

    fun loadNoteList() {
        viewModelScope.launch {
            event(NoteEvent.NoteListTitle(getAllNoteTitleUseCase()))
        }
    }

    fun saveUri(uri: Uri?) {
        viewModelScope.launch {
            event(NoteEvent.CsvTitle(setUriUseCase(uri)))
        }
    }

    private fun event(event: NoteEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class NoteEvent {
        data class NoteListTitle(val noteTitles: List<String>) : NoteEvent()
        data class CsvTitle(val csvTitle: String) : NoteEvent()
        data class ShowToast(val text: String) : NoteEvent()
    }
}