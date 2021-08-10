package com.example.bard.ui.note

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
import com.example.bard.constants.Constants
import com.example.bard.data.AddContent
import com.example.bard.databinding.ActivityNoteBinding
import com.example.bard.ui.base.BaseActivity
import com.example.bard.ui.base.EventObserver
import com.example.bard.ui.base.OnSingleClickListener
import com.example.bard.ui.detail.DetailActivity
import com.example.bard.ui.helper.CsvHelper
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

    /**
     * csv result
     */
    private val selectCsvResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            makeList(CsvHelper().readCsvData(_result.data?.data))
        }
    }

    /**
     * detail result
     */
    private val detailActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _result ->
        if (_result.resultCode == Activity.RESULT_OK) {
            println(">>>>>>>>> 성공")
        }
    }

    private fun setUpListener() {
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

    private fun makeList(list: List<Array<String>>) {
        val wordList = mutableListOf<AddContent>()
        /* TODO : 단어나 뜻이 하나 없을때는 어떻게 되는지 확인해야 함 // 컬럼이 3개이상일 때는 어떻게 되는지 확인 필요 */
        list.forEach {
            println(it.joinToString(","))
            val word = it.joinToString(",").split(",")
            wordList.add(AddContent(word[0], word[1]))
//            println(">>>>>>>>> result >>>>> ${it.contentDeepToString()}")
        }

        println(">>>>>>>>>>>>>> wordList >>>>>>. $wordList")
//        vm.saveNote(wordList)
    }

    private fun subscribeViewModel() {
        /* 단어장 목록 */
        vm.noteList.observe(this, {
            binding.apply {
                rvNote.layoutManager = LinearLayoutManager(this@NoteActivity)
                rvNote.adapter = NoteTitleAdapter(it).apply {
                    titleClickListener(object : NoteTitleAdapter.TitleClickListener {
                        override fun titleClickListener(title: String) {
                            openNoteDetail(title)
                        }
                    })
                }
            }
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