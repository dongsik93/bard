package com.example.bard.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.data.AddContent
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
import com.example.bard.ui.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DsRepository
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
            repository.findWordWithTitle(title).catch { e ->
                /* 에러처리 */
                handleError(e)
            }.collect {
                _wordList.value = it
            }
        }
    }

    fun findIdByTitle(title: String) {
        viewModelScope.launch {
            _noteId.value = repository.getNoteId(title).id
        }
    }

    private fun handleError(exception: Throwable) {
        val message = exception.message ?: ""
        _error.value = Event(message)
    }
}