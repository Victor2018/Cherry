package com.victor.lib.common.view.widget.piv.transformer

import android.graphics.Canvas
import android.widget.ImageView
import com.victor.lib.common.view.widget.piv.ScrollTransformImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HorizontalParallaxTransformer
 * Author: Victor
 * Date: 2020/7/27 下午 02:41
 * Description: 
 * -----------------------------------------------------------------
 */
class HorizontalParallaxTransformer : ViewTransformer() {

    override fun onAttached(view: ScrollTransformImageView) {
        view.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun apply(view: ScrollTransformImageView, canvas: Canvas, viewX : Int, viewY : Int) {
        if (view.scaleType == ImageView.ScaleType.CENTER_CROP && view.drawable != null) {
            val imageWidth = view.drawable.intrinsicWidth
            val imageHeight = view.drawable.intrinsicHeight

            val viewWidth = view.width - view.paddingLeft - view.paddingRight
            val viewHeight = view.height - view.paddingTop - view.paddingBottom

            val deviceWidth = view.resources.displayMetrics.widthPixels

            if (viewX < -viewWidth || viewX > deviceWidth) return

            // if this is true then the scaling must be based on the height of the
            // image view and the bitmap image itself
            if (imageWidth * viewHeight > viewWidth * imageHeight) {
                val scale = viewHeight.toFloat() / imageHeight.toFloat()
                val invisibleHorizontalArea = imageWidth * scale - viewWidth

                val x = centeredX(viewX, viewWidth, deviceWidth)
                val translationScale = invisibleHorizontalArea / (deviceWidth + viewWidth)
                canvas.translate(-x * translationScale, 0f)
            }
        }
    }

}