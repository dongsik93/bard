package com.example.bard.presentation.add

import androidx.lifecycle.viewModelScope
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.usecases.GetNoteByIdUseCase
import com.example.bard.domain.usecases.SetNoteUseCase
import com.example.bard.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val setNoteUseCase: SetNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<AddEvent>()
    val eventFlow: SharedFlow<AddEvent> = _eventFlow

    fun saveNote(noteItem: NoteData, ) {
        viewModelScope.launch {
            event(AddEvent.Success(setNoteUseCase(noteItem)))
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(noteId).apply {
                event(AddEvent.Note(NoteData(noteId, first, second,)))
            }
        }
    }

    private fun event(event: AddEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class AddEvent {
        data class Note(val data: NoteData) : AddEvent()
        data class Success(val id: Int) : AddEvent()
    }
}