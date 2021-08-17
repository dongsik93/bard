package com.example.bard.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.usecases.GetNoteUseCase
import com.example.bard.domain.usecases.SetNoteUseCase
import com.example.bard.domain.model.NoteData
import com.example.bard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val setNoteUseCase: SetNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
) : BaseViewModel() {

    private val _noteData: MutableLiveData<NoteData> = MutableLiveData()
    val noteData: LiveData<NoteData> = _noteData

    fun saveNote(noteItem: NoteData, ) {
        viewModelScope.launch {
            setNoteUseCase.saveNote(noteItem)
        }
    }

    fun findNoteById(noteId: Int) {
        viewModelScope.launch {
            val res = getNoteUseCase.getNoteById(noteId)
            _noteData.value = NoteData(
                noteId,
                res.first,
                res.second.toMutableList(),
            )
        }
    }
}