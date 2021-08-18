package com.example.bard.ui.detail

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityDetailBinding
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.model.NoteData
import com.example.bard.ui.add.AddActivity
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.base.EventObserver
import com.example.bard.ui.base.OnSingleClickListener
import com.example.bard.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

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

        setUpListener()
        subscribeViewModel()
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

    private fun subscribeViewModel() {
        /* 단어리스트 */
        vm.wordList.observe(this, { _data ->
            binding.tvDetailTitle.text = _data.title
            binding.rvDetailWords.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = DetailAdapter(_data.wordList)
            }
        })

        /* 에러처리 */
        vm.error.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        /* 수정하기 */
        vm.noteId.observe(this, {
            val intent = Intent(this, AddActivity::class.java).apply {
                putExtra("test", it)
            }
            editActivityResult.launch(intent)
        })
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