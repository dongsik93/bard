package com.example.bard.ui.detail

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.constants.Constants
import com.example.bard.databinding.ActivityDetailBinding
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.base.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    private val vm: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun getLayoutId() = R.layout.activity_detail

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        binding = getViewDataBinding()
        with(intent) {
            getStringExtra(Constants.NOTE_TITLE)?.let {
                binding.tvDetailTitle.text = it
                vm.findWordByTitle(it)
            }
        }

        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        /* 단어리스트 */
        vm.wordList.observe(this, { _list ->
            binding.rvDetailWords.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = DetailAdapter(_list)
            }
        })

        /* 에러처리 */
        vm.error.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }
}