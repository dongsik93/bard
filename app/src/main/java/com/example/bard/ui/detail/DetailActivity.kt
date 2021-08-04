package com.example.bard.ui.detail

import androidx.activity.viewModels
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
                vm.findWordByTitle(it)
            }
        }
    }
}