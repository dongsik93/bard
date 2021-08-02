package com.example.bard.ui.add

import androidx.lifecycle.viewModelScope
import com.example.bard.data.AddContent
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: DsRepository
) : BaseViewModel() {

    fun saveNote(
        itemList: List<AddContent>,
        title: String,
    ) {
        viewModelScope.launch {
            repository.saveNote(itemList, title)
        }
    }
}