package com.example.bard.ui.detail

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.constants.Constants
import com.example.bard.data.AddContent
import com.example.bard.data.NoteData
import com.example.bard.databinding.ActivityDetailBinding
import com.example.bard.ui.add.AddActivity
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.base.EventObserver
import com.example.bard.ui.base.OnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    private val vm: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun getLayoutId() = R.layout.activity_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    private var noteData = listOf<AddContent>()

    override fun setActivity() {
        binding = getViewDataBinding()
        with(intent) {
            getStringExtra(Constants.NOTE_TITLE)?.let {
                /* TODO : 제목 수정할 수 있도록 변경 // note id로 쿼리하도록 변경하기 */
                binding.tvDetailTitle.text = it
                vm.findWordByTitle(it)
            }
        }

        setUpListener()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        /* 단어리스트 */
        vm.wordList.observe(this, { _list ->
            binding.rvDetailWords.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = DetailAdapter(_list)
                noteData = _list
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
            startActivity(intent)
            finish()
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

}