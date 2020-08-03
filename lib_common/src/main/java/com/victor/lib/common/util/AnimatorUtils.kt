package com.victor.lib.common.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimatorUtils
 * Author: Victor
 * Date: 2020/8/3 下午 05:17
 * Description: 
 * -----------------------------------------------------------------
 */
object AnimatorUtils {
    fun getScaleAnimator(view: View, startScale: Float, endScale: Float): AnimatorSet {
        val set = AnimatorSet()
        val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, startScale, endScale)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, startScale, endScale)
        set.playTogether(scaleXAnimator, scaleYAnimator)
        return set
    }
}