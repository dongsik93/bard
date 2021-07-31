package com.example.bard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityMainBinding
import com.example.bard.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun getBindingVariable() = BR._all

    override fun getViewModel(): MainViewModel {
        TODO("Not yet implemented")
    }

    override fun createActivity() {
        TODO("Not yet implemented")
    }

}