package com.victor.lib.common.view.widget.kenburnsview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: KenBurnsView
 * Author: Victor
 * Date: 2020/7/25 下午 05:49
 * Description: 
 * -----------------------------------------------------------------
 */
@SuppressLint("AppCompatCustomView")
class KenBurnsView: ImageView {
    /** Delay between a pair of frames at a 60 FPS frame rate.  */
    private val FRAME_DELAY = 1000 / 60.toLong()

    /** Matrix used to perform all the necessary transition transformations.  */
    private val mMatrix: Matrix = Matrix()

    /** The [TransitionGenerator] implementation used to perform the transitions between
     * rects. The default [TransitionGenerator] is [RandomTransitionGenerator].  */
    private var mTransGen: TransitionGenerator = RandomTransitionGenerator()

    /** A [KenBurnsView.TransitionListener] to be notified when
     * a transition starts or ends.  */
    private var mTransitionListener: TransitionListener? = null

    /** The ongoing transition.  */
    private var mCurrentTrans: Transition? = null

    /** The rect that holds the bounds of this view.  */
    private var mViewportRect = RectF()

    /** The rect that holds the bounds of the current [Drawable].  */
    private var mDrawableRect: RectF? = null

    /** The progress of the animation, in milliseconds.  */
    private var mElapsedTime: Long = 0

    /** The time, in milliseconds, of the last animation frame.
     * This is useful to increment [.mElapsedTime] regardless
     * of the amount of time the animation has been paused.  */
    private var mLastFrameTime: Long = 0

    /** Controls whether the the animation is running.  */
    private var mPaused = false

    /** Indicates whether the parent constructor was already called.
     * This is needed to distinguish if the image is being set before
     * or after the super class constructor returns.  */
    private var mInitialized = false


    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?,defStyle: Int) : super(context, attrs,defStyle) {
        mInitialized = true;
        // Attention to the super call here!
        super.setScaleType(ImageView.ScaleType.MATRIX);
    }

    override fun setScaleType(scaleType: ScaleType?) {
        // It'll always be center-cropped by default.
    }


    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        when (visibility) {
            View.VISIBLE -> resume()
            else -> pause()
        }
    }


    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        handleImageChange()
    }


    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        handleImageChange()
    }


    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        handleImageChange()
    }


    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        handleImageChange()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        restart()
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDraw(canvas: Canvas?) {
        val d = drawable
        if (!mPaused && d != null) {
            if (mDrawableRect!!.isEmpty) {
                updateDrawableBounds()
            } else if (hasBounds()) {
                if (mCurrentTrans == null) { // Starting the first transition.
                    startNewTransition()
                }
                if (mCurrentTrans?.getDestinyRect() != null) { // If null, it's supposed to stop.
                    mElapsedTime += System.currentTimeMillis() - mLastFrameTime
                    val currentRect: RectF = mCurrentTrans?.getInterpolatedRect(mElapsedTime)!!
                    val widthScale = mDrawableRect!!.width() / currentRect.width()
                    val heightScale = mDrawableRect!!.height() / currentRect.height()
                    // Scale to make the current rect match the smallest drawable dimension.
                    val currRectToDrwScale =
                        Math.min(widthScale, heightScale)
                    // Scale to make the current rect match the viewport bounds.
                    val vpWidthScale = mViewportRect.width() / currentRect.width()
                    val vpHeightScale = mViewportRect.height() / currentRect.height()
                    val currRectToVpScale =
                        Math.min(vpWidthScale, vpHeightScale)
                    // Combines the two scales to fill the viewport with the current rect.
                    val totalScale = currRectToDrwScale * currRectToVpScale
                    val translX =
                        totalScale * (mDrawableRect!!.centerX() - currentRect.left)
                    val translY =
                        totalScale * (mDrawableRect!!.centerY() - currentRect.top)

                    /* Performs matrix transformations to fit the content
                       of the current rect into the entire view. */mMatrix.reset()
                    mMatrix.postTranslate(
                        -mDrawableRect!!.width() / 2,
                        -mDrawableRect!!.height() / 2
                    )
                    mMatrix.postScale(totalScale, totalScale)
                    mMatrix.postTranslate(translX, translY)
                    imageMatrix = mMatrix

                    // Current transition is over. It's time to start a new one.
                    if (mElapsedTime >= mCurrentTrans!!.duration!!) {
                        fireTransitionEnd(mCurrentTrans)
                        startNewTransition()
                    }
                } else { // Stopping? A stop event has to be fired.
                    fireTransitionEnd(mCurrentTrans)
                }
            }
            mLastFrameTime = System.currentTimeMillis()
            postInvalidateDelayed(FRAME_DELAY)
        }
        super.onDraw(canvas)
    }


    /**
     * Generates and starts a transition.
     */
    private fun startNewTransition() {
        if (!hasBounds()) {
            return  // Can't start transition if the drawable has no bounds.
        }
        mCurrentTrans = mTransGen.generateNextTransition(mDrawableRect, mViewportRect)
        mElapsedTime = 0
        mLastFrameTime = System.currentTimeMillis()
        fireTransitionStart(mCurrentTrans)
    }


    /**
     * Creates a new transition and starts over.
     */
    fun restart() {
        val width = width
        val height = height
        if (width == 0 || height == 0) {
            return  // Can't call restart() when view area is zero.
        }
        updateViewport(width.toFloat(), height.toFloat())
        updateDrawableBounds()
        startNewTransition()
    }


    /**
     * Checks whether this view has bounds.
     * @return
     */
    private fun hasBounds(): Boolean {
        return !mViewportRect.isEmpty
    }


    /**
     * Fires a start event on [.mTransitionListener];
     * @param transition the transition that just started.
     */
    private fun fireTransitionStart(transition: Transition?) {
        if (mTransitionListener != null && transition != null) {
            mTransitionListener?.onTransitionStart(transition)
        }
    }


    /**
     * Fires an end event on [.mTransitionListener];
     * @param transition the transition that just ended.
     */
    private fun fireTransitionEnd(transition: Transition?) {
        if (mTransitionListener != null && transition != null) {
            mTransitionListener?.onTransitionEnd(transition)
        }
    }


    /**
     * Sets the [TransitionGenerator] to be used in animations.
     * @param transgen the [TransitionGenerator] to be used in animations.
     */
    fun setTransitionGenerator(transgen: TransitionGenerator) {
        mTransGen = transgen
        startNewTransition()
    }


    /**
     * Updates the viewport rect. This must be called every time the size of this view changes.
     * @param width the new viewport with.
     * @param height the new viewport height.
     */
    private fun updateViewport(width: Float, height: Float) {
        mViewportRect[0f, 0f, width] = height
    }


    /**
     * Updates the drawable bounds rect. This must be called every time the drawable
     * associated to this view changes.
     */
    private fun updateDrawableBounds() {
        if (mDrawableRect == null) {
            mDrawableRect = RectF()
        }
        val d = drawable
        if (d != null && d.intrinsicHeight > 0 && d.intrinsicWidth > 0) {
            mDrawableRect!![0f, 0f, d.intrinsicWidth.toFloat()] = d.intrinsicHeight.toFloat()
        }
    }


    /**
     * This method is called every time the underlying image
     * is changed.
     */
    private fun handleImageChange() {
        updateDrawableBounds()
        /* Don't start a new transition if this event
         was fired during the super constructor execution.
         The view won't be ready at this time. Also,
         don't start it if this view size is still unknown. */if (mInitialized) {
            startNewTransition()
        }
    }


    fun setTransitionListener(transitionListener: TransitionListener) {
        mTransitionListener = transitionListener
    }


    /**
     * Pauses the Ken Burns Effect animation.
     */
    fun pause() {
        mPaused = true
    }


    /**
     * Resumes the Ken Burns Effect animation.
     */
    fun resume() {
        mPaused = false
        // This will make the animation to continue from where it stopped.
        mLastFrameTime = System.currentTimeMillis()
        invalidate()
    }


    /**
     * A transition listener receives notifications when a transition starts or ends.
     */
    interface TransitionListener {
        /**
         * Notifies the start of a transition.
         * @param transition the transition that just started.
         */
        fun onTransitionStart(transition: Transition?)

        /**
         * Notifies the end of a transition.
         * @param transition the transition that just ended.
         */
        fun onTransitionEnd(transition: Transition?)
    }
}