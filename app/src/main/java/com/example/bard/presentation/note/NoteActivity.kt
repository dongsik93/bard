package com.example.bard.presentation.note

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bard.BR
import com.example.bard.BardBase.Companion.appContext
import com.example.bard.R
import com.example.bard.databinding.ActivityNoteBinding
import com.example.bard.presentation.base.BaseActivity
import com.example.bard.presentation.base.EventObserver
import com.example.bard.presentation.base.OnSingleClickListener
import com.example.bard.presentation.detail.DetailActivity
import com.example.bard.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteActivity : BaseActivity<ActivityNoteBinding, NoteViewModel>() {
    private val vm: NoteViewModel by viewModels()
    private lateinit var binding: ActivityNoteBinding

    override fun getLayoutId() = R.layout.activity_note

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        binding = getViewDataBinding()
        setUpListener()
        subscribeViewModel()
    }

    private val noteTitleAdapter = NoteTitleAdapter()

    /**
     * csv result
     */
    private val selectCsvResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            vm.saveUri(_result.data?.data)
        }
    }

    /**
     * detail result
     */
    private val detailActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            if (_result.data?.getBooleanExtra("result", false) == true) {
                vm.loadNoteList()
            }
        }
    }

    private fun setUpListener() {
        /* 불러오기 */
        binding.tvFile.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                if (ActivityCompat.checkSelfPermission(
                        appContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        appContext, Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openFileExplorer()
                } else {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 200
                    )
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.isNotEmpty()) {
                openFileExplorer()
            }
        }
    }

    /**
     * openFileExplorer
     * @desc 파일 선택기
     */
    private fun openFileExplorer() {
        val selection = Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .setType("text/comma-separated-values")

        val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, selection)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        selectCsvResult.launch(chooserIntent)
    }

    private fun updateTitleList(title: String) {
        mutableListOf<String>().also { _list ->
            _list.addAll(noteTitleAdapter.getAllItem())
            _list.add(0, title)
            noteTitleAdapter.apply {
                updateItem(_list)
                notifyItemChanged(0)
            }
        }
    }

    private fun subscribeViewModel() {
        /* 단어장 목록 */
        vm.noteList.observe(this, {
            binding.apply {
                rvNote.layoutManager = LinearLayoutManager(this@NoteActivity)
                noteTitleAdapter.updateItem(it.toMutableList())
                rvNote.adapter = noteTitleAdapter.apply {
                    titleClickListener(object : NoteTitleAdapter.TitleClickListener {
                        override fun titleClickListener(title: String) {
                            openNoteDetail(title)
                        }
                    })
                }
            }
        })

        /* csv file title */
        vm.csvTitle.observe(this, {
            updateTitleList(it)
        })

        /* 에러 */
        vm.error.observe(this, EventObserver { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun openNoteDetail(title: String) {
        val detail = Intent(this, DetailActivity::class.java).apply {
            putExtra(Constants.NOTE_TITLE, title)
        }
        detailActivityResult.launch(detail)
    }
}