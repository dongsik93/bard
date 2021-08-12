package com.example.bard.ui.add

import android.view.View
import android.widget.Toast
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

    private var adapter = setDefaultAdapter()

    override fun setActivity() {
        binding = getViewDataBinding()
        initRecyclerView()
        with(intent) {
            val noteId = getIntExtra("test", -1)
            if (noteId > 0) {
                vm.findNoteById(noteId)
            } else {
                setAdapter()
            }
        }

        setListener()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        vm.noteData.observe(this, { _noteData ->
            binding.apply {
                etTitle.setText(_noteData.first)
                adapter = AddItemAdapter(_noteData.second.toMutableList())
                setAdapter()
            }
        })
    }

    private fun setAdapter() {
        binding.rvAddContent.adapter = adapter
    }

    private fun setListener() {
        binding.tvAdd.setOnClickListener {
            adapter.addItem()
        }

        /* 단어장 저장 */
        binding.tvSave.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                checkData {
                    println(">>>>>>>> all item >>>>> ${adapter.getAllItem()}")
                    vm.saveNote(adapter.getAllItem(), binding.etTitle.text.toString())
                }
            }
        })
    }

    /**
     * 유효성 검사 후 데이터 input
     */
    private fun checkData(func: () -> Unit) {
        binding.apply {
            if (etTitle.text.isEmpty()) {
                showToast("제목을 입력해 주세요")
            } else {
                func.invoke()
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        binding.apply {
            rvAddContent.layoutManager = LinearLayoutManager(this@AddActivity)
        }
    }

    private fun setDefaultAdapter(): AddItemAdapter {
        val itemList = mutableListOf<AddContent>()
        for (i in 0..3) { itemList.add(AddContent()) }
        return AddItemAdapter(itemList)
    }
}