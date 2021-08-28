package com.example.bard.presentation.card

import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityCardBinding
import com.example.bard.domain.model.AddContent
import com.example.bard.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardActivity : BaseActivity<ActivityCardBinding, CardViewModel>() {
    private val vm: CardViewModel by viewModels()
    private lateinit var binding: ActivityCardBinding

    override fun getLayoutId() = R.layout.activity_card

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        binding = getViewDataBinding()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        binding.vpCard.apply {
            adapter = CardPagerAdapter(this@CardActivity, makeTestData())
            registerOnPageChangeCallback(viewPagerCallback)
        }
    }

    private val viewPagerCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            println(">>>>>>>>>>> 페이지 넘어감 >>> $position")
        }
    }

    private fun makeTestData(): List<AddContent> {
        return listOf(
            AddContent("단어1", "뜻 1"),
            AddContent("단어2", "뜻 2"),
            AddContent("단어3", "뜻 3"),
            AddContent("단어4", "뜻 4"),
        )
    }
}