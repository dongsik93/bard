package com.example.bard.presentation.views.card

import androidx.lifecycle.viewModelScope
import com.example.bard.domain.model.NoteData
import com.example.bard.domain.usecases.GetWordsByTitleUseCase
import com.example.bard.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val getWordsByTitleUseCase: GetWordsByTitleUseCase,
) : BaseViewModel() {

    private val _wordList = MutableSharedFlow<NoteData>()
    val wordList: SharedFlow<NoteData>
        get() = _wordList

    fun findWordByTitle(title: String) {
        viewModelScope.launch {
            _wordList.emit(getWordsByTitleUseCase(title))
        }
    }
}