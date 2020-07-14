package com.victor.lib.common.view.widget.searchview

import android.animation.Animator
import android.animation.ValueAnimator

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimatorExtensions
 * Author: Victor
 * Date: 2020/7/13 下午 04:48
 * Description: 
 * -----------------------------------------------------------------
 */
fun ValueAnimator.endListener(onAnimationEnd: ()->Unit){
    addListener(object : SimpleAnimationListener(){
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            onAnimationEnd.invoke()
        }
    })
}