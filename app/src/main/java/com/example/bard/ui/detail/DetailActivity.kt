package com.example.bard.ui.detail

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityDetailBinding
import com.example.bard.ui.base.BaseActivity
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
            getStringExtra("test")?.let {
                binding.tvDetailTitle.text = it
                vm.findWordByTitle(it)
            }
        }

        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        vm.wordList.observe(this, { _list ->
            binding.rvDetailWords.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = DetailAdapter(_list)
            }
        })
    }
}