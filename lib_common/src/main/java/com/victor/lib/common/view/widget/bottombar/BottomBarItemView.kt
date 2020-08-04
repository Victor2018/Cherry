package com.victor.lib.common.view.widget.bottombar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.victor.lib.common.R
import com.victor.lib.common.view.widget.bottombar.ReadableBottomBar.Companion.ANIMATION_DURATION

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BottomBarItemView
 * Author: Victor
 * Date: 2020/7/13 下午 03:04
 * Description: 
 * -----------------------------------------------------------------
 */
class BottomBarItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private val layoutView = LayoutInflater.from(context).inflate(R.layout.layout_bottombar_item, this, true)
    private val textView = layoutView.findViewById<AppCompatTextView>(R.id.textView)
    private val imageView = layoutView.findViewById<AppCompatImageView>(R.id.imageView)

    private var translateUpAnimation: TranslateAnimation? = null
    private var translateDownAnimation: TranslateAnimation? = null

    private lateinit var animatedView: View

    fun setText(text: String) {
        textView.text = text
    }

    fun setIconDrawable(drawable: Drawable) {
        imageView.setImageDrawable(drawable)
    }

    fun setItemType(itemType: ReadableBottomBar.ItemType) {
        animatedView = when (itemType) {
            ReadableBottomBar.ItemType.Text -> textView
            ReadableBottomBar.ItemType.Icon -> imageView
        }
        animatedView.visibility = View.INVISIBLE
        animatedView.bringToFront()
    }

    fun setTabColor(tabColor: Int) {
        textView.setBackgroundColor(tabColor)
        imageView.setBackgroundColor(tabColor)
    }

    fun setTextSize(textSize: Float) {
        textView.textSize = textSize
    }

    fun setTextColor(textColor: Int) {
        textView.setTextColor(textColor)
    }


    fun setIconColor(iconColor: Int) {
        imageView.setColorFilter(iconColor)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeAnimations()
    }

    fun select() {
        if (animatedView == null) return
        if (translateUpAnimation == null) return
        animatedView.startAnimation(translateUpAnimation)
    }

    fun deselect() {
        if (animatedView == null) return
        if (translateUpAnimation == null) return
        animatedView.startAnimation(translateDownAnimation)
    }

    private fun initializeAnimations() {
        translateUpAnimation = TranslateAnimation(0f, 0f, height.toFloat(), 0f).apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                }

                override fun onAnimationStart(animation: Animation?) {
                    animatedView.visibility = View.VISIBLE
                }

            })
        }

        translateDownAnimation = TranslateAnimation(0f, 0f, 0f, height.toFloat()).apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    animatedView.visibility = View.INVISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
        }
    }

}