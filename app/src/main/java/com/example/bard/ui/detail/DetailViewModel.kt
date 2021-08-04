package com.example.bard.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bard.data.AddContent
import com.example.bard.repository.DsRepository
import com.example.bard.ui.base.BaseViewModel
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

    fun findWordByTitle(title: String) {
        viewModelScope.launch {
            repository.findWordWithTitle(title).catch {
                /* 에러처리 */
                println(">>>>>>>>>> 에러에러")
            }.collect {
                println(">>>>>>>>>>>> 단어장 리스트 >>>>. $it")
            }
        }
    }
}