package com.example.bard.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityMainBinding
import com.example.bard.ui.add.AddActivity
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.note.NoteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private val vm: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun getLayoutId() = R.layout.activity_main
    override fun getBindingVariable() = BR._all
    override fun getViewModel() = vm
    override fun setActivity() {
        binding = getViewDataBinding()
        setListener()
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            println(">>>>>>>>> 성공")
        }
    }

    private fun setListener() {
        binding.tvAdd.setOnClickListener {
            val addActivity = Intent(this, AddActivity::class.java)
            activityResult.launch(addActivity)
        }

        binding.tvNote.setOnClickListener {
            val noteActivity = Intent(this, NoteActivity::class.java)
            activityResult.launch(noteActivity)
        }
    }
}