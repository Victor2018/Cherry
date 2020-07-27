package com.victor.lib.common.util

import android.app.Activity
import android.content.Context
import android.view.WindowManager


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DensityUtil
 * Author: Victor
 * Date: 2020/7/27 上午 10:55
 * Description: 
 * -----------------------------------------------------------------
 */
object DensityUtil {
    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        return display.height
    }

    fun getScreenHeight(activity: Activity): Int {
        return activity.windowManager.defaultDisplay.height
    }

    fun getScreenWidth(activity: Activity): Int {
        return activity.windowManager.defaultDisplay.width
    }

    /**
     * 根据手机的分辨率 dp 的单位 转成 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率 px(像素) 的单位 转成 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    // 将px值转换为sp值
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }
}