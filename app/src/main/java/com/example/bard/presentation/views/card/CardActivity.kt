package com.example.bard.presentation.views.card

import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityCardBinding
import com.example.bard.domain.model.NoteData
import com.example.bard.presentation.base.BaseActivity
import com.example.bard.presentation.ext.repeatOnStart
import com.example.bard.presentation.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CardActivity : BaseActivity<ActivityCardBinding, CardViewModel>() {
    private val vm: CardViewModel by viewModels()
    private lateinit var binding: ActivityCardBinding

    override fun getLayoutId() = R.layout.activity_card

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        binding = getViewDataBinding()
        with(intent) {
            getStringExtra(Constants.CARD_TITLE)?.let {
                vm.findWordByTitle(it)
            }
        }

        repeatOnStart {
            vm.wordList.collect { _noteData -> setUpViewPager(_noteData) }
        }
    }

    private fun setUpViewPager(noteData: NoteData) {
        binding.vpCard.apply {
            adapter = CardPagerAdapter(this@CardActivity, noteData.wordList)
            registerOnPageChangeCallback(viewPagerCallback)
        }
    }

    private val viewPagerCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }
}