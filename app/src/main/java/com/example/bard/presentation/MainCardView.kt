package com.example.bard.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.bard.R
import com.example.bard.databinding.MainCardViewBinding

class MainCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = DataBindingUtil.inflate<MainCardViewBinding>(
            inflater,
            R.layout.main_card_view,
            this,
            true
        )

        context.obtainStyledAttributes(
            attrs,
            R.styleable.MainCardView,
            defStyleAttr,
            defStyleRes
        ).apply {
            val imgSrc = getResourceId(R.styleable.MainCardView_cardIcon, -1)
            val title = getString(R.styleable.MainCardView_cardTitle)
            val backgroundColor = getResourceId(R.styleable.MainCardView_cardColor, -1)

            binding.apply {
                tvTitle.text = title
                if (imgSrc > 0) ivCard.setImageResource(imgSrc)
                if (backgroundColor > 0) clCard.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(backgroundColor, null))
            }

            recycle()
        }
    }
}