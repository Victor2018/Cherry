package com.victor.lib.common.util

import androidx.annotation.ColorInt
import com.victor.lib.common.R
import com.victor.lib.common.app.App
import org.victor.funny.util.ResUtils
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorUtil
 * Author: Victor
 * Date: 2020/7/8 下午 06:04
 * Description: 
 * -----------------------------------------------------------------
 */
object ColorUtil {
    fun getDefaultRandomColor(): Int {
        val colors = intArrayOf(
            App.get().getResources().getColor(R.color.color_E6FCFC),
            App.get().getResources().getColor(R.color.color_E6FBFD),
            App.get().getResources().getColor(R.color.color_E5FAFE),
            App.get().getResources().getColor(R.color.color_E5F9FE))
        val random = Random()
        return colors[random.nextInt(colors.size)]
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    fun modifyAlpha(@ColorInt color: Int, alpha: Int): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    fun modifyAlpha(@ColorInt color: Int, alpha: Float): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }

    fun getColorByHashCode (hashCode: Int): Int {
        val colors = ResUtils.getIntArrayRes(R.array.search_filter_colors)
        val color: Int = colors?.get(hashCode % colors?.size)!!
        return color
    }
}