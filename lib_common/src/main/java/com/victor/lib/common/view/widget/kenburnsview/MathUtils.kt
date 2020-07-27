package com.victor.lib.common.view.widget.kenburnsview

import android.graphics.RectF




/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MathUtils
 * Author: Victor
 * Date: 2020/7/25 下午 05:58
 * Description: 
 * -----------------------------------------------------------------
 */
object MathUtils {
    /**
     * Truncates a float number `f` to `decimalPlaces`.
     * @param f the number to be truncated.
     * @param decimalPlaces the amount of decimals that `f`
     * will be truncated to.
     * @return a truncated representation of `f`.
     */
    fun truncate(f: Float, decimalPlaces: Int): Float {
        val decimalShift =
            Math.pow(10.0, decimalPlaces.toDouble()).toFloat()
        return Math.round(f * decimalShift) / decimalShift
    }


    /**
     * Checks whether two [RectF] have the same aspect ratio.
     * @param r1 the first rect.
     * @param r2  the second rect.
     * @return `true` if both rectangles have the same aspect ratio,
     * `false` otherwise.
     */
    fun haveSameAspectRatio(r1: RectF?, r2: RectF?): Boolean {
        // Reduces precision to avoid problems when comparing aspect ratios.
        val srcRectRatio: Float =
            truncate(
                getRectRatio(
                    r1!!
                ), 3
            )
        val dstRectRatio: Float =
            truncate(
                getRectRatio(
                    r2!!
                ), 3
            )

        // Compares aspect ratios that allows for a tolerance range of [0, 0.01]
        return Math.abs(srcRectRatio - dstRectRatio) <= 0.01f
    }


    /**
     * Computes the aspect ratio of a given rect.
     * @param rect the rect to have its aspect ratio computed.
     * @return the rect aspect ratio.
     */
    fun getRectRatio(rect: RectF): Float {
        return rect.width() / rect.height()
    }
}