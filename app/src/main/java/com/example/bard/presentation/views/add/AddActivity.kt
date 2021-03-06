package com.example.bard.presentation.views.add

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityAddBinding
import com.example.bard.domain.model.AddContent
import com.example.bard.domain.model.NoteData
import com.example.bard.presentation.base.BaseActivity
import com.example.bard.presentation.base.OnSingleClickListener
import com.example.bard.presentation.ext.repeatOnStart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {
    private val vm: AddViewModel by viewModels()
    private lateinit var binding: ActivityAddBinding

    override fun getLayoutId() = R.layout.activity_add
    override fun getBindingVariable() = BR._all
    override fun getViewModel() = vm

    private var adapter = setDefaultAdapter()
    private var isEdit = false

    override fun setActivity() {
        binding = getViewDataBinding()
        initRecyclerView()
        with(intent) {
            val noteId = getIntExtra("test", -1)
            if (noteId > 0) {
                vm.findNoteById(noteId)
                isEdit = true
            } else {
                setAdapter()
            }
        }

        repeatOnStart {
            vm.eventFlow.collect { event -> handleEvent(event) }
        }

        setListener()
    }

    private fun handleEvent(event: AddViewModel.AddEvent) {
        when (event) {
            is AddViewModel.AddEvent.Note -> {
                binding.apply {
                    etTitle.setText(event.data.title)
                    adapter = AddItemAdapter(event.data)
                    setAdapter()
                }
            }
            is AddViewModel.AddEvent.Success -> {
                if (isEdit) {
                    showToast("단어장 수정이 완료되었습니다.")
                    setResults(event.id)
                } else {
                    showToast("단어장 생성이 완료되었습니다.")
                    finish()
                }
            }
        }
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
                    val noteData = adapter.getAllItem().apply {
                        title = binding.etTitle.text.toString()
                    }
                    vm.saveNote(noteData)
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
        return AddItemAdapter(
            NoteData(-1, "", itemList)
        )
    }

    private fun setResults(noteId: Int) {
        val data = Intent().apply {
            putExtra("result", isEdit)
            putExtra("noteId", noteId)
        }
        setResult(RESULT_OK, data)
        finish()
    }
}