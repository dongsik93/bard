package com.example.bard.presentation.card

import android.animation.*
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.viewModels
import com.example.bard.BR
import com.example.bard.R
import com.example.bard.databinding.ActivityCardBinding
import com.example.bard.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
/**
 * TODO : 단어 외우는 페이지용 카드
 * TODO : 뷰페이저 이용해서 만들기
 */
class CardActivity : BaseActivity<ActivityCardBinding, CardViewModel>() {
    private val vm: CardViewModel by viewModels()
    private lateinit var binding: ActivityCardBinding

    override fun getLayoutId() = R.layout.activity_card

    override fun getBindingVariable() = BR._all

    override fun getViewModel() = vm

    override fun setActivity() {
        binding = getViewDataBinding()
        setCardAnimation()
    }

    private var firstRun = true

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
            .with(animatorBuilder(binding.textViewBottom2))

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
        val textAnimator = AnimatorInflater.loadAnimator(this, R.animator.fade_translate)
        textAnimator.setTarget(targetView)
        return textAnimator
    }
}