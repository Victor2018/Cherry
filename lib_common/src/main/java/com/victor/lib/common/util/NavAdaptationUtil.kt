package com.victor.lib.common.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NavAdaptationUtil
 * Author: Victor
 * Date: 2020/8/11 上午 11:32
 * Description: Android全面屏虚拟导航栏适配
 * -----------------------------------------------------------------
 */

object NavAdaptationUtil {
    const val TAG = "NavAdaptationUtil"

    public interface OnNavigationStateListener {
        fun onNavigationState(isNavShowing: Boolean,navHeight: Int)
    }

    fun adaptationNav(activity: Activity?) {
        if (activity == null) {
            return
        }
        isNavigationBarExist(activity,object : OnNavigationStateListener {
            override fun onNavigationState(isNavShowing: Boolean, navHeight: Int) {
                if (isNavShowing) {
                    var contentView: View? = getContentView(activity)
                    contentView?.setPadding(0,0,0,navHeight)
                }
            }

        })
    }

    fun isNavigationBarExist(activity: Activity?, onNavigationStateListener: OnNavigationStateListener) {
        if (activity == null) {
            return
        }
        val height = getNavigationHeight(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            activity.window.decorView.setOnApplyWindowInsetsListener { v, windowInsets ->
                var isShowing = false
                var insetBottom = 0
                if (windowInsets != null) {
                    insetBottom = windowInsets.systemWindowInsetBottom
                    isShowing = insetBottom == height
                }
                if (onNavigationStateListener != null && insetBottom <= height) {
                    onNavigationStateListener.onNavigationState(isShowing, insetBottom)
                }
                windowInsets
            }
        }
    }



    fun getNavigationHeight(activity: Context?): Int {
        if (activity == null) {
            return 0
        }
        val resources = activity.resources
        val resourceId = resources.getIdentifier(
            "navigation_bar_height",
            "dimen", "android"
        )
        var height = 0
        if (resourceId > 0) {
            //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    fun getContentView (activity: Activity?): View? {
        var contentView: View? = null

        var decorView = activity?.findViewById<View>(android.R.id.content) as ViewGroup

        if (decorView.childCount > 0) {
            contentView = decorView.getChildAt(0) as ViewGroup
        }
        return contentView
    }
}