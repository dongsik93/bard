package com.example.bard.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.data.AddContent
import com.example.bard.data.NoteData
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
import com.example.bard.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: DsRepository
) : BaseViewModel() {

    private val _noteList: MutableLiveData<List<String>> = MutableLiveData()
    val noteList: LiveData<List<String>>
        get() = _noteList

    private val _error: MutableLiveData<Event<String>> = MutableLiveData()
    val error: LiveData<Event<String>> = _error

    init {
        loadNoteList()
    }

    private fun loadNoteList() {
        viewModelScope.launch {
            repository.loadNoteTitle().catch { e ->
                /* 에러 */
                handleError(e)
            }.collect {
                /* 성공 */
                _noteList.value = it
            }
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
            repository.saveNote(noteData)
        }
    }
}