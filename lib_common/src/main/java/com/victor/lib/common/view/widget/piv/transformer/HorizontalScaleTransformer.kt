package com.victor.lib.common.view.widget.piv.transformer

import android.graphics.Canvas
import com.victor.lib.common.view.widget.piv.ScrollTransformImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HorizontalScaleTransformer
 * Author: Victor
 * Date: 2020/7/27 下午 02:40
 * Description: This transformer will scale image view when scrolled horizontally
 * -----------------------------------------------------------------
 */
class HorizontalScaleTransformer(minScale : Float) : ViewTransformer() {

    var minScale : Float = minScale
        set (value) {
            field = Math.min(minScale, 1f)
        }

    override fun onAttached(view: ScrollTransformImageView) {}

    override fun onDetached(view: ScrollTransformImageView) {}

    override fun apply(view: ScrollTransformImageView, canvas: Canvas, viewX : Int, viewY : Int) {
        // TODO: Change the algorithm so that it uses screen centered coordinate

        val maxDScale = 1f - minScale
        if (maxDScale > 0f) {
            val viewWidth = view.width - view.paddingLeft - view.paddingRight
            val viewHeight = view.height - view.paddingTop - view.paddingBottom

            val deviceWidth = view.resources.displayMetrics.widthPixels

            val x = when {
                viewX < -viewWidth -> return
                viewX > deviceWidth -> return
                else -> viewX
            }

            val center = (deviceWidth - viewWidth) / 2f
            val scaleFactor = maxDScale / ((deviceWidth + viewWidth) / 2f)
            val scale = 1f - (Math.abs(x - center) * scaleFactor)

            canvas.scale(scale, scale, viewWidth / 2f, viewHeight / 2f)
        }
    }

}