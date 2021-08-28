package com.example.bard.presentation.card

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bard.domain.model.AddContent

class CardPagerAdapter(
    fragment: FragmentActivity,
    private val noteData: List<AddContent>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = noteData.size

    override fun createFragment(position: Int) = CardPagerFragment.newInstance(noteData[position])
}