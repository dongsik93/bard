package com.example.bard.ui.add

import androidx.activity.viewModels
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityAddBinding
import com.example.bard.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {
    private val vm: AddViewModel by viewModels()
    override fun getLayoutId() = R.layout.activity_add

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        
    }
}