package com.example.bard.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel>
    : AppCompatActivity() {

    private lateinit var viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initDataBinding()
        createActivity()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int
    abstract fun getViewModel(): V

    abstract fun createActivity()

    private fun initDataBinding() {
        viewDataBinding.apply {
            DataBindingUtil.setContentView<T>(this@BaseActivity, getLayoutId())
            lifecycleOwner = this@BaseActivity
            setVariable(getBindingVariable(), getViewModel())
            executePendingBindings()
        }
    }
}