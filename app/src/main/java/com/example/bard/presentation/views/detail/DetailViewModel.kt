package com.example.bard.presentation.views.detail

import androidx.lifecycle.viewModelScope
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.usecases.GetNoteByIdUseCase
import com.example.bard.domain.usecases.GetNoteIdUseCase
import com.example.bard.domain.usecases.GetWordsByTitleUseCase
import com.example.bard.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteIdUseCase,
    private val getWordsByTitleUseCase: GetWordsByTitleUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
) : BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<DetailEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

//    fun findWordByTitle(title: String)
//        viewModelScope.launch {
//            event(DetailEvent.WordList(getWordsByTitleUseCase(title)))
//        }
//    }

    fun findIdByTitle(title: String) {
        viewModelScope.launch {
            event(DetailEvent.NoteId(getNoteUseCase(title)))
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            val res = getNoteByIdUseCase(noteId)
            event(DetailEvent.WordList(NoteData(noteId, res.first, res.second)))
        }
    }

    private fun event(event: DetailEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class DetailEvent {
        data class WordList(val data: NoteData) : DetailEvent()
        data class NoteId(val id: Int) : DetailEvent()
        data class ShowToast(val text: String) : DetailEvent()
    }
}