package com.example.bard.ui.note

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityNoteBinding
import com.example.bard.ui.base.BaseActivity
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

    private fun subscribeViewModel() {
        vm.noteList.observe(this, {
            binding.apply {
                rvNote.layoutManager = LinearLayoutManager(this@NoteActivity)
                rvNote.adapter = NoteTitleAdapter(it)
            }
        })
    }
}