package com.victor.lib.common.view.widget.searchview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DataBindingExtensions
 * Author: Victor
 * Date: 2020/7/13 下午 04:48
 * Description: 
 * -----------------------------------------------------------------
 */
fun <T: ViewDataBinding> Context.inflate(@LayoutRes layoutRes: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(this), layoutRes, null, false)
}

fun <T : ViewDataBinding> ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToParent: Boolean = true): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToParent)
}

inline fun View.afterMeasured(crossinline f: View.(View) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f(this@afterMeasured)
            }
        }
    })
}