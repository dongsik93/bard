package com.example.bard.presentation.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.usecases.GetWordsByTitleUseCase
import com.example.bard.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val getWordsByTitleUseCase: GetWordsByTitleUseCase,
) : BaseViewModel() {

    private val _wordList: MutableLiveData<NoteData> = MutableLiveData()
    val wordList: LiveData<NoteData>
        get() = _wordList

    fun findWordByTitle(title: String) {
        viewModelScope.launch {
            _wordList.value = getWordsByTitleUseCase(title)
        }
    }
}