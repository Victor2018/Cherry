package com.victor.lib.common.view.widget.kenburnsview

import android.graphics.RectF
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.victor.lib.common.view.widget.kenburnsview.MathUtils.getRectRatio
import com.victor.lib.common.view.widget.kenburnsview.MathUtils.haveSameAspectRatio
import com.victor.lib.common.view.widget.kenburnsview.MathUtils.truncate
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RandomTransitionGenerator
 * Author: Victor
 * Date: 2020/7/25 下午 06:02
 * Description: 
 * -----------------------------------------------------------------
 */
class RandomTransitionGenerator: TransitionGenerator {
    companion object {
        /** Default value for the transition duration in milliseconds.  */
        val DEFAULT_TRANSITION_DURATION: Long = 10000
    }

    /** Minimum rect dimension factor, according to the maximum one.  */
    private val MIN_RECT_FACTOR = 0.75f

    /** Random object used to generate arbitrary rects.  */
    private val mRandom: Random = Random(System.currentTimeMillis())

    /** The duration, in milliseconds, of each transition.  */
    private var mTransitionDuration: Long = 0

    /** The [Interpolator] to be used to create transitions.  */
    private var mTransitionInterpolator: Interpolator? = null

    /** The last generated transition.  */
    private var mLastGenTrans: Transition? = null

    /** The bounds of the drawable when the last transition was generated.  */
    private var mLastDrawableBounds: RectF? = null

    constructor(): this(DEFAULT_TRANSITION_DURATION, AccelerateDecelerateInterpolator())
    constructor(transitionDuration: Long, transitionInterpolator: Interpolator) {
        setTransitionDuration(transitionDuration)
        setTransitionInterpolator(transitionInterpolator)
    }

    override fun generateNextTransition(drawableBounds: RectF?, viewport: RectF?): Transition? {
        val firstTransition = mLastGenTrans == null
        var drawableBoundsChanged = true
        var viewportRatioChanged = true

        var srcRect: RectF? = null
        var dstRect: RectF? = null

        if (!firstTransition) {
            dstRect = mLastGenTrans!!.getDestinyRect()
            drawableBoundsChanged = !drawableBounds!!.equals(mLastDrawableBounds)
            viewportRatioChanged =
                !haveSameAspectRatio(
                    dstRect,
                    viewport
                )
        }

        if (dstRect == null || drawableBoundsChanged || viewportRatioChanged) {
            srcRect = generateRandomRect(drawableBounds!!, viewport!!)
        } else {
            /* Sets the destiny rect of the last transition as the source one
             if the current drawable has the same dimensions as the one of
             the last transition. */
            srcRect = dstRect
        }
        dstRect = generateRandomRect(drawableBounds!!, viewport!!)

        mLastGenTrans = Transition(
            srcRect!!, dstRect!!, mTransitionDuration,
            mTransitionInterpolator!!
        )

        mLastDrawableBounds = RectF(drawableBounds)

        return mLastGenTrans
    }

    /**
     * Generates a random rect that can be fully contained within `drawableBounds` and
     * has the same aspect ratio of `viewportRect`. The dimensions of this random rect
     * won't be higher than the largest rect with the same aspect ratio of `viewportRect`
     * that `drawableBounds` can contain. They also won't be lower than the dimensions
     * of this upper rect limit weighted by `MIN_RECT_FACTOR`.
     * @param drawableBounds the bounds of the drawable that will be zoomed and panned.
     * @param viewportRect the bounds of the view that the drawable will be shown.
     * @return an arbitrary generated rect with the same aspect ratio of `viewportRect`
     * that will be contained within `drawableBounds`.
     */
    private fun generateRandomRect(drawableBounds: RectF, viewportRect: RectF): RectF? {
        val drawableRatio =
            getRectRatio(drawableBounds)
        val viewportRectRatio =
            getRectRatio(viewportRect)
        val maxCrop: RectF
        maxCrop = if (drawableRatio > viewportRectRatio) {
            val r =
                drawableBounds.height() / viewportRect.height() * viewportRect.width()
            val b = drawableBounds.height()
            RectF(0f, 0f, r, b)
        } else {
            val r = drawableBounds.width()
            val b =
                drawableBounds.width() / viewportRect.width() * viewportRect.height()
            RectF(0f, 0f, r, b)
        }
        val randomFloat =
            truncate(
                mRandom.nextFloat(),
                2
            )
        val factor = MIN_RECT_FACTOR + (1 - MIN_RECT_FACTOR) * randomFloat
        val width = factor * maxCrop.width()
        val height = factor * maxCrop.height()
        val widthDiff = (drawableBounds.width() - width).toInt()
        val heightDiff = (drawableBounds.height() - height).toInt()
        val left = if (widthDiff > 0) mRandom.nextInt(widthDiff) else 0
        val top = if (heightDiff > 0) mRandom.nextInt(heightDiff) else 0
        return RectF(left.toFloat(), top.toFloat(), left + width, top + height)
    }


    /**
     * Sets the duration, in milliseconds, for each transition generated.
     * @param transitionDuration the transition duration.
     */
    fun setTransitionDuration(transitionDuration: Long) {
        mTransitionDuration = transitionDuration
    }


    /**
     * Sets the [Interpolator] for each transition generated.
     * @param interpolator the transition interpolator.
     */
    fun setTransitionInterpolator(interpolator: Interpolator) {
        mTransitionInterpolator = interpolator
    }
}