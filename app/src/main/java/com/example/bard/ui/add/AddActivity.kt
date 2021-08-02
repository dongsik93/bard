package com.example.bard.ui.add

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.data.AddContent
import com.example.bard.databinding.ActivityAddBinding
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.base.OnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {
    private val vm: AddViewModel by viewModels()
    private lateinit var binding: ActivityAddBinding

    override fun getLayoutId() = R.layout.activity_add
    override fun getBindingVariable() = BR._all
    override fun getViewModel() = vm

    private val adapter by lazy { setAdapter() }

    override fun setActivity() {
        binding = getViewDataBinding()
        initRecyclerView()
        setListener()
    }

    private fun setListener() {
        binding.tvAdd.setOnClickListener {
            adapter.addItem()
        }

        /* 단어장 저장 */
        binding.tvSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                vm.saveNote(adapter.getAllItem(), binding.etTitle.text.toString())
            }
        })
    }

    private fun initRecyclerView() {
        binding.apply {
            rvAddContent.layoutManager = LinearLayoutManager(this@AddActivity)
            rvAddContent.adapter = adapter
        }
    }

    private fun setAdapter(): AddItemAdapter {
        val itemList = mutableListOf<AddContent>()
        for (i in 0..3) { itemList.add(AddContent.default()) }
        return AddItemAdapter(itemList)
    }
}