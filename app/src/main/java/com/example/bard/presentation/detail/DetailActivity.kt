package com.example.bard.presentation.detail

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityDetailBinding
import com.example.bard.presentation.add.AddActivity
import com.example.bard.presentation.base.BaseActivity
import com.example.bard.presentation.base.OnSingleClickListener
import com.example.bard.presentation.ext.repeatOnStart
import com.example.bard.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    private val vm: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun getLayoutId() = R.layout.activity_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    private var isEdit = false

    override fun setActivity() {
        binding = getViewDataBinding()
        with(intent) {
            getStringExtra(Constants.NOTE_TITLE)?.let {
                vm.findWordByTitle(it)
            }
        }

        repeatOnStart {
            lifecycleScope.launch {
                vm.eventFlow.collect { event -> handleEvent(event) }
            }
        }

        setUpListener()
    }

    private val editActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            if (_result.data?.getBooleanExtra("result", false) == true) {
                vm.findNoteById(_result.data?.getIntExtra("noteId", -1) ?: -1)
                isEdit = true
                setResults()
            }
        }
    }

    private fun handleEvent(event: DetailViewModel.DetailEvent) {
        when (event) {
            /* 에러처리 */
            is DetailViewModel.DetailEvent.ShowToast -> {
                Toast.makeText(this, event.text, Toast.LENGTH_SHORT).show()
            }
            /* 단어리스트 */
            is DetailViewModel.DetailEvent.WordList -> {
                binding.tvDetailTitle.text = event.data.title
                binding.rvDetailWords.apply {
                    layoutManager = LinearLayoutManager(this@DetailActivity)
                    adapter = DetailAdapter(event.data.wordList)
                }
            }
            /* 수정하기 */
            is DetailViewModel.DetailEvent.NoteId -> {
                val intent = Intent(this, AddActivity::class.java).apply {
                    putExtra("test", event.id)
                }
                editActivityResult.launch(intent)
            }
        }
    }

    private fun setUpListener() {
        /* 수정하기 */
        binding.tvEdit.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                vm.findIdByTitle(binding.tvDetailTitle.text.toString())
            }
        })
    }

    private fun setResults() {
        val data = Intent().apply {
            putExtra("result", isEdit)
        }
        setResult(RESULT_OK, data)
    }
}