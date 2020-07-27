package com.victor.lib.common.view.widget.piv.transformer

import android.graphics.Canvas
import com.victor.lib.common.view.widget.piv.ScrollTransformImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewTransformer
 * Author: Victor
 * Date: 2020/7/27 下午 02:38
 * Description:
 * Base class for all transformer, extends this class to implement custom
 * image view transformation before it's being rendered on the screen
 * -----------------------------------------------------------------
 */
abstract class ViewTransformer {

    /**
     * This will be called when it's being set into the ScrollTransformImageView
     */
    open fun onAttached(view : ScrollTransformImageView) {}

    /**
     * This will be called when it's being removed or replaced by other viewTransformer
     * from the ScrollTransformImageView
     */
    open fun onDetached(view : ScrollTransformImageView) {}

    /**
     * apply will be called every time the view scrolled and before rendered
     */
    abstract fun apply(view: ScrollTransformImageView, canvas: Canvas, viewX : Int, viewY : Int)

    /**
     * Convert top,left (0,0) coordinate
     * to the middle of the screen
     */
    protected fun centeredX(x : Int, viewWidth : Int, screenWidth : Int) = x + (viewWidth / 2) - (screenWidth / 2)
    protected fun centeredY(y : Int, viewHeight : Int, screenHeight : Int) = (screenHeight / 2) - (y + (viewHeight / 2))
}