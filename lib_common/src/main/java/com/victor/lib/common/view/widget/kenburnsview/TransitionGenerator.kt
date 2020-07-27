package com.victor.lib.common.view.widget.kenburnsview

import android.graphics.RectF




/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TransitionGenerator
 * Author: Victor
 * Date: 2020/7/25 下午 06:02
 * Description: 
 * -----------------------------------------------------------------
 */
interface TransitionGenerator {
    /**
     * Generates the next transition to be played by the [KenBurnsView].
     * @param drawableBounds the bounds of the drawable to be shown in the [KenBurnsView].
     * @param viewport the rect that represents the viewport where
     * the transition will be played in. This is usually the bounds of the
     * [KenBurnsView].
     * @return a [Transition] object to be played by the [KenBurnsView].
     */
    fun generateNextTransition(
        drawableBounds: RectF?,
        viewport: RectF?
    ): Transition?
}