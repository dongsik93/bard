package com.example.bard.ui.note

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityNoteBinding
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteActivity : BaseActivity<ActivityNoteBinding, NoteViewModel>() {
    private val vm: NoteViewModel by viewModels()
    private lateinit var binding: ActivityNoteBinding

    override fun getLayoutId() = R.layout.activity_note

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        binding = getViewDataBinding()
        subscribeViewModel()
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            println(">>>>>>>>> 성공")
        }
    }

    private fun subscribeViewModel() {
        vm.noteList.observe(this, {
            binding.apply {
                rvNote.layoutManager = LinearLayoutManager(this@NoteActivity)
                rvNote.adapter = NoteTitleAdapter(it).apply {
                    titleClickListener(object : NoteTitleAdapter.TitleClickListener {
                        override fun titleClickListener(title: String) {
                            println(">>>>>>>>>> title !!!!! $title")
                            openNoteDetail(title)
                        }
                    })
                }
            }
        })
    }

    private fun openNoteDetail(title: String) {
        val detail = Intent(this, DetailActivity::class.java).apply {
            putExtra("test", title)
        }
        activityResult.launch(detail)
    }
}