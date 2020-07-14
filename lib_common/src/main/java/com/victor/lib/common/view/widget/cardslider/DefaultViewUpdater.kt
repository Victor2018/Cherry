package com.victor.lib.common.view.widget.cardslider

import android.view.View
import androidx.core.view.ViewCompat

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DefaultViewUpdater
 * Author: Victor
 * Date: 2020/7/11 下午 06:32
 * Description: 
 * -----------------------------------------------------------------
 */
open class DefaultViewUpdater: CardSliderLayoutManager.ViewUpdater {
    val SCALE_LEFT = 0.65f
    val SCALE_CENTER = 0.95f
    val SCALE_RIGHT = 0.8f
    val SCALE_CENTER_TO_LEFT = SCALE_CENTER - SCALE_LEFT
    val SCALE_CENTER_TO_RIGHT = SCALE_CENTER - SCALE_RIGHT

    val Z_CENTER_1 = 12
    val Z_CENTER_2 = 16
    val Z_RIGHT = 8

    private var cardWidth = 0
    private var activeCardLeft = 0
    private var activeCardRight = 0
    private var activeCardCenter = 0
    private var cardsGap = 0f

    private var transitionEnd = 0
    private var transitionDistance = 0
    private var transitionRight2Center = 0f

    private var lm: CardSliderLayoutManager? = null

    private var previewView: View? = null

    override fun onLayoutManagerInitialized(lm: CardSliderLayoutManager) {
        this.lm = lm
        cardWidth = lm.getCardWidth()
        activeCardLeft = lm.getActiveCardLeft()
        activeCardRight = lm.getActiveCardRight()
        activeCardCenter = lm.getActiveCardCenter()
        cardsGap = lm.getCardsGap()
        transitionEnd = activeCardCenter
        transitionDistance = activeCardRight - transitionEnd
        val centerBorder = (cardWidth - cardWidth * SCALE_CENTER) / 2f
        val rightBorder = (cardWidth - cardWidth * SCALE_RIGHT) / 2f
        val right2centerDistance =
            activeCardRight + centerBorder - (activeCardRight - rightBorder)
        transitionRight2Center = right2centerDistance - cardsGap
    }

    override fun updateView(view: View, position: Float) {
        val scale: Float
        val alpha: Float
        val z: Float
        val x: Float
        if (position < 0) {
            val ratio = lm!!.getDecoratedLeft(view).toFloat() / activeCardLeft
            scale = SCALE_LEFT + SCALE_CENTER_TO_LEFT * ratio
            alpha = 0.1f + ratio
            z = Z_CENTER_1 * ratio
            x = 0f
        } else if (position < 0.5f) {
            scale = SCALE_CENTER
            alpha = 1f
            z = Z_CENTER_1.toFloat()
            x = 0f
        } else if (position < 1f) {
            val viewLeft = lm!!.getDecoratedLeft(view)
            val ratio =
                (viewLeft - activeCardCenter).toFloat() / (activeCardRight - activeCardCenter)
            scale = SCALE_CENTER - SCALE_CENTER_TO_RIGHT * ratio
            alpha = 1f
            z = Z_CENTER_2.toFloat()
            x = if (Math.abs(transitionRight2Center) < Math.abs(
                    transitionRight2Center * (viewLeft - transitionEnd) / transitionDistance
                )
            ) {
                -transitionRight2Center
            } else {
                -transitionRight2Center * (viewLeft - transitionEnd) / transitionDistance
            }
        } else {
            scale = SCALE_RIGHT
            alpha = 1f
            z = Z_RIGHT.toFloat()
            if (previewView != null) {
                val prevViewScale: Float
                val prevTransition: Float
                val prevRight: Int
                val isFirstRight =
                    lm!!.getDecoratedRight(previewView!!) <= activeCardRight
                if (isFirstRight) {
                    prevViewScale = SCALE_CENTER
                    prevRight = activeCardRight
                    prevTransition = 0f
                } else {
                    prevViewScale = ViewCompat.getScaleX(previewView)
                    prevRight = lm!!.getDecoratedRight(previewView!!)
                    prevTransition = ViewCompat.getTranslationX(previewView)
                }
                val prevBorder = (cardWidth - cardWidth * prevViewScale) / 2
                val currentBorder = (cardWidth - cardWidth * SCALE_RIGHT) / 2
                val distance =
                    lm!!.getDecoratedLeft(view) + currentBorder - (prevRight - prevBorder + prevTransition)
                val transition = distance - cardsGap
                x = -transition
            } else {
                x = 0f
            }
        }
        ViewCompat.setScaleX(view, scale)
        ViewCompat.setScaleY(view, scale)
        ViewCompat.setZ(view, z)
        ViewCompat.setTranslationX(view, x)
        ViewCompat.setAlpha(view, alpha)
        previewView = view
    }

    open fun getLayoutManager(): CardSliderLayoutManager? {
        return lm
    }
}