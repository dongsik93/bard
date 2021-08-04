package com.example.bard.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
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

    init {
        loadNoteList()
    }

    private fun loadNoteList() {
        viewModelScope.launch {
            repository.loadNoteTitle().catch {
                /* 에러 */
                println(">>>>>>>>>>> 에러")
            }.collect {
                /* 성공 */
                _noteList.value = it
            }
        }
    }
}