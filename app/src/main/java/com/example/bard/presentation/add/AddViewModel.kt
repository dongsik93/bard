package com.example.bard.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.usecases.GetNoteByIdUseCase
import com.example.bard.domain.usecases.SetNoteUseCase
import com.example.bard.presentation.base.BaseViewModel
import com.example.bard.presentation.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val setNoteUseCase: SetNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : BaseViewModel() {

    private val _noteData: MutableLiveData<NoteData> = MutableLiveData()
    val noteData: LiveData<NoteData> = _noteData

    private val _success: MutableLiveData<Event<Int>> = MutableLiveData()
    val success: LiveData<Event<Int>> = _success

    fun saveNote(noteItem: NoteData, ) {
        viewModelScope.launch {
            _success.value = Event(setNoteUseCase(noteItem))
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(noteId).apply {
                _noteData.value = NoteData(noteId, first, second,)
            }
        }
    }
}