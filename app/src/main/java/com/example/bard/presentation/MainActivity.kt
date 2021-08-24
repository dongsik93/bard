package com.example.bard.presentation

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityMainBinding
import com.example.bard.presentation.add.AddActivity
import com.example.bard.presentation.base.BaseActivity
import com.example.bard.presentation.card.CardActivity
import com.example.bard.presentation.note.NoteActivity
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
        setProfile()
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            println(">>>>>>>>> 성공")
        }
    }

    private fun setProfile() {
        Glide.with(this)
            .load(R.drawable.sample_image)
            .placeholder(R.drawable.sample_image)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfile)
    }

    private fun setListener() {
        binding.apply {
            mcvAdd.setOnClickListener {
                val addActivity = Intent(this@MainActivity, AddActivity::class.java)
                activityResult.launch(addActivity)
            }
            mcvList.setOnClickListener {
                val noteActivity = Intent(this@MainActivity, NoteActivity::class.java)
                activityResult.launch(noteActivity)
            }
            mcvStudy.setOnClickListener {
                val cardActivity = Intent(this@MainActivity, CardActivity::class.java)
                activityResult.launch(cardActivity)
            }
        }
    }
}