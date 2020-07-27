package com.victor.lib.common.view.widget.kenburnsview

import android.graphics.RectF
import android.view.animation.Interpolator
import com.victor.lib.common.view.widget.kenburnsview.MathUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Transition
 * Author: Victor
 * Date: 2020/7/25 下午 05:56
 * Description: 
 * -----------------------------------------------------------------
 */
class Transition(var srcRect: RectF,
                  var dstRect: RectF,
                  var duration: Long? = 0,
                  var interpolator: Interpolator) {
    /** The rect the transition will start from.  */
    private var mSrcRect: RectF? = null

    /** The rect the transition will end at.  */
    private var mDstRect: RectF? = null

    /** An intermediary rect that changes in every frame according to the transition progress.  */
    private val mCurrentRect = RectF()

    /** Precomputed width difference between [.mSrcRect] and [.mDstRect].  */
    private var mWidthDiff = 0f

    /** Precomputed height difference between [.mSrcRect] and [.mDstRect].  */
    private var mHeightDiff = 0f

    /** Precomputed X offset between the center points of
     * [.mSrcRect] and [.mDstRect].  */
    private var mCenterXDiff = 0f

    /** Precomputed Y offset between the center points of
     * [.mSrcRect] and [.mDstRect].  */
    private var mCenterYDiff = 0f

    /** The duration of the transition in milliseconds. The default duration is 5000 ms.  */
    private var mDuration: Long = 0

    /** The [Interpolator] used to perform the transitions between rects.  */
    private var mInterpolator: Interpolator


   init {
       if (!MathUtils.haveSameAspectRatio(srcRect, dstRect)) {
           throw IncompatibleRatioException()
       }
       mSrcRect = srcRect
       mDstRect = dstRect
       mDuration = duration!!
       mInterpolator = interpolator

       // Precomputes a few variables to avoid doing it in onDraw().
       mWidthDiff = dstRect.width() - srcRect.width()
       mHeightDiff = dstRect.height() - srcRect.height()
       mCenterXDiff = dstRect.centerX() - srcRect.centerX()
       mCenterYDiff = dstRect.centerY() - srcRect.centerY()
   }


    /**
     * Gets the rect that will take the scene when a Ken Burns transition starts.
     * @return the rect that starts the transition.
     */
    fun getSourceRect(): RectF? {
        return mSrcRect
    }


    /**
     * Gets the rect that will take the scene when a Ken Burns transition ends.
     * @return the rect that ends the transition.
     */
    fun getDestinyRect(): RectF? {
        return mDstRect
    }


    /**
     * Gets the current rect that represents the part of the image to take the scene
     * in the current frame.
     * @param elapsedTime the elapsed time since this transition started.
     */
    fun getInterpolatedRect(elapsedTime: Long): RectF? {
        val elapsedTimeFraction = elapsedTime / mDuration.toFloat()
        val interpolationProgress = Math.min(elapsedTimeFraction, 1f)
        val interpolation: Float = mInterpolator?.getInterpolation(interpolationProgress)!!
        val currentWidth = mSrcRect!!.width() + interpolation * mWidthDiff
        val currentHeight = mSrcRect!!.height() + interpolation * mHeightDiff
        val currentCenterX = mSrcRect!!.centerX() + interpolation * mCenterXDiff
        val currentCenterY = mSrcRect!!.centerY() + interpolation * mCenterYDiff
        val left = currentCenterX - currentWidth / 2
        val top = currentCenterY - currentHeight / 2
        val right = left + currentWidth
        val bottom = top + currentHeight
        mCurrentRect[left, top, right] = bottom
        return mCurrentRect
    }


    /**
     * Gets the duration of this transition.
     * @return the duration, in milliseconds.
     */
    fun getDuration(): Long {
        return mDuration
    }
}