package com.example.bard.presentation.card

import android.animation.*
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bard.R
import com.example.bard.databinding.PagerCardBinding
import com.example.bard.domain.model.AddContent

class CardPagerFragment : Fragment() {

    private lateinit var binding: PagerCardBinding
    private var firstRun = true

    private val noteData: AddContent by lazy { arguments?.getParcelable("noteData") ?: AddContent("", "") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.pager_card, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNoteData(noteData)
        setCardAnimation()
    }

    private fun setUpNoteData(data: AddContent) {
        if (data.word.isNotEmpty()) {
            binding.tvWord.text = data.word
        }

        if (data.meaning.isNotEmpty()) {
            binding.tvMeaning.text = data.meaning
        }
    }

    private fun setCardAnimation() {
        val bottomImageValueAnimator: ValueAnimator =
            ObjectAnimator.ofFloat(binding.avdBottom, "translationY", 262f)
        bottomImageValueAnimator.duration = 1000
        bottomImageValueAnimator.interpolator = OvershootInterpolator()

        val topAnimationSet = AnimatorSet()
        topAnimationSet.play(valueAnimatorBuilder(binding.materialMaterialShow))
            .with(valueAnimatorBuilder(binding.avdUp))

        val bottomAnimationSet = AnimatorSet()
        bottomAnimationSet.play(bottomImageValueAnimator)
            .with(animatorBuilder(binding.textViewBottom))
            .with(animatorBuilder(binding.tvMeaning))

        binding.materialMaterialShow.setOnClickListener { v ->
            if (firstRun) {
                val d = binding.avdUp.drawable
                val drawable = binding.avdBottom.drawable
                if (d is AnimatedVectorDrawable) {
                    val avdBottom = drawable as AnimatedVectorDrawable
                    d.start()
                    avdBottom.start()
                    bottomAnimationSet.start()
                    topAnimationSet.start()
                    binding.imageExpandIcon.animate().rotation(180f).setDuration(500).start()
                    Handler().postDelayed({ topAnimationSet.reverse() }, 300)
                }
                firstRun = false
            } else {
                bottomAnimationSet.reverse()
                binding.imageExpandIcon.animate().rotation(0f).setDuration(500).start()
                firstRun = true
            }
        }
    }

    private fun valueAnimatorBuilder(view: View?): ValueAnimator {
        val valueAnimator: ValueAnimator = ObjectAnimator.ofFloat(view, "translationY", 20f)
        valueAnimator.duration = 800
        valueAnimator.interpolator = LinearInterpolator()
        return valueAnimator
    }

    private fun animatorBuilder(targetView: View?): Animator? {
        val textAnimator = AnimatorInflater.loadAnimator(requireActivity(), R.animator.fade_translate)
        textAnimator.setTarget(targetView)
        return textAnimator
    }

    companion object {
        fun newInstance(noteData: AddContent) = CardPagerFragment().apply {
            arguments = Bundle().apply {
                putParcelable("noteData", noteData)
            }
        }
    }
}