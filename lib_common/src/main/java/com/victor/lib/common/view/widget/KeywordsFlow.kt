package com.victor.lib.common.view.widget

import android.R
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import android.widget.FrameLayout
import android.widget.TextView
import java.util.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: KeywordsFlow
 * Author: Victor
 * Date: 2020/7/29 上午 11:50
 * Description: 
 * -----------------------------------------------------------------
 */
class KeywordsFlow: FrameLayout, ViewTreeObserver.OnGlobalLayoutListener {
    companion object {
        const val IDX_X = 0
        const val IDX_Y = 1
        const val IDX_TXT_LENGTH = 2
        const val IDX_DIS_Y = 3

        /** 由外至内的动画。  */
        const val ANIMATION_IN = 1

        /** 由内至外的动画。  */
        const val ANIMATION_OUT = 2

        /** 位移动画类型：从外围移动到坐标点。  */
        const val OUTSIDE_TO_LOCATION = 1

        /** 位移动画类型：从坐标点移动到外围。  */
        const val LOCATION_TO_OUTSIDE = 2

        /** 位移动画类型：从中心点移动到坐标点。  */
        const val CENTER_TO_LOCATION = 3

        /** 位移动画类型：从坐标点移动到中心点。  */
        const val LOCATION_TO_CENTER = 4
        const val ANIM_DURATION = 800L
        var MAX = 10
        const val TEXT_SIZE_MAX = 25
        const val TEXT_SIZE_MIN = 15
    }

    var itemClickListener: OnClickListener? = null
    var interpolator: Interpolator? = null
    var animAlpha2Opaque: AlphaAnimation? = null
    var animAlpha2Transparent: AlphaAnimation? = null
    var animScaleLarge2Normal: ScaleAnimation? = null
    var animScaleNormal2Large:ScaleAnimation? = null
    var animScaleZero2Normal:ScaleAnimation? = null
    var animScaleNormal2Zero: ScaleAnimation? = null

    /** 存储显示的关键字。  */
    var vecKeywords: Vector<String>? = null
    var width: Int? = 0
    var height:Int? = 0

    /**
     * go2Show()中被赋值为true，标识开发人员触发其开始动画显示。<br></br>
     * 本标识的作用是防止在填充keywrods未完成的过程中获取到width和height后提前启动动画。<br></br>
     * 在show()方法中其被赋值为false。<br></br>
     * 真正能够动画显示的另一必要条件：width 和 height不为0。<br></br>
     */
    private var enableShow = false
    private var random: Random? = null

    /**
     * @see //ANIMATION_IN
     *
     * @see //ANIMATION_OUT
     *
     * @see //OUTSIDE_TO_LOCATION
     *
     * @see //LOCATION_TO_OUTSIDE
     *
     * @see //LOCATION_TO_CENTER
     *
     * @see //CENTER_TO_LOCATION
     *
     */
    private var txtAnimInType = 0

    /**
     * @see //ANIMATION_IN
     *
     * @see //ANIMATION_OUT
     *
     * @see //OUTSIDE_TO_LOCATION
     *
     * @see //LOCATION_TO_OUTSIDE
     *
     * @see //LOCATION_TO_CENTER
     *
     * @see //CENTER_TO_LOCATION
     *
     */
    private var txtAnimOutType = 0

    /** 最近一次启动动画显示的时间。  */
    private var lastStartAnimationTime: Long = 0

    /** 动画运行时间。  */
    private var animDuration: Long = 0

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?,defStyle: Int) : super(context, attrs,defStyle) {
        lastStartAnimationTime = 0L
        animDuration = ANIM_DURATION
        random = Random()
        vecKeywords = Vector(MAX)
        viewTreeObserver.addOnGlobalLayoutListener(this)
        interpolator = AnimationUtils.loadInterpolator(
            getContext(),
            R.anim.decelerate_interpolator
        )
        animAlpha2Opaque = AlphaAnimation(0.0f, 1.0f)
        animAlpha2Transparent = AlphaAnimation(1.0f, 0.0f)
        animScaleLarge2Normal = ScaleAnimation(2f, 1f, 2f, 1f)
        animScaleNormal2Large = ScaleAnimation(1f, 2f, 1f, 2f)
        animScaleZero2Normal = ScaleAnimation(0f, 1f, 0f, 1f)
        animScaleNormal2Zero = ScaleAnimation(1f, 0f, 1f, 0f)
    }

    fun feedKeyword(keyword: String?): Boolean {
        var result = false
        if (vecKeywords!!.size < MAX) {
            result = vecKeywords!!.add(keyword)
        }
        return result
    }

    /**
     * 开始动画显示。<br></br>
     * 之前已经存在的TextView将会显示退出动画。<br></br>
     *
     * @return 正常显示动画返回true；反之为false。返回false原因如下：<br></br>
     * 1.时间上不允许，受lastStartAnimationTime的制约；<br></br>
     * 2.未获取到width和height的值。<br></br>
     */
    fun go2Show(animType: Int): Boolean {
        if (System.currentTimeMillis() - lastStartAnimationTime > animDuration) {
            enableShow = true
            if (animType == ANIMATION_IN) {
                txtAnimInType = OUTSIDE_TO_LOCATION
                txtAnimOutType = LOCATION_TO_CENTER
            } else if (animType == ANIMATION_OUT) {
                txtAnimInType = CENTER_TO_LOCATION
                txtAnimOutType = LOCATION_TO_OUTSIDE
            }
            disapper()
            return show()
        }
        return false
    }

    private fun disapper() {
        val size = childCount
        for (i in size - 1 downTo 0) {
            val txt = getChildAt(i) as TextView
            if (txt.visibility == View.GONE) {
                removeView(txt)
                continue
            }
            val layParams =
                txt.layoutParams as LayoutParams
            // Log.d("ANDROID_LAB", txt.getText() + " leftM=" +
            // layParams.leftMargin + " topM=" + layParams.topMargin
            // + " width=" + txt.getWidth());
            val xy =
                intArrayOf(layParams.leftMargin, layParams.topMargin, txt.width)
            val animSet: AnimationSet =
                getAnimationSet(xy, width!! shr 1, height!! shr 1, txtAnimOutType)!!
            txt.startAnimation(animSet)
            animSet.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    txt.setOnClickListener(null)
                    txt.isClickable = false
                    txt.visibility = View.GONE
                }
            })
        }
    }

    private fun show(): Boolean {
        if (width!! > 0 && height!! > 0 && vecKeywords != null && vecKeywords!!.size > 0 && enableShow) {
            enableShow = false
            lastStartAnimationTime = System.currentTimeMillis()
            //找到中心点
            val xCenter = width!! shr 1
            val yCenter = height!! shr 1
            //关键字的个数。
            val size = vecKeywords!!.size
            val xItem = width!! / size
            val yItem = height!! / size
            val listX = LinkedList<Int>()
            val listY = LinkedList<Int>()
            for (i in 0 until size) {
                // 准备随机候选数，分别对应x/y轴位置
                listX.add(i * xItem)
                listY.add(i * yItem + (yItem shr 2))
            }
            // TextView[] txtArr = new TextView[size];
            val listTxtTop = LinkedList<TextView>()
            val listTxtBottom = LinkedList<TextView>()
            for (i in 0 until size) {
                val keyword = vecKeywords!![i]
                // 随机颜色
                val ranColor: Int =
                    Color.argb(
                        255,
                        random!!.nextInt(256),
                        random!!.nextInt(256),
                        random!!.nextInt(256)
                    )
                // 随机位置，糙值
                val xy: IntArray = randomXY(random!!, listX, listY, xItem)!!
                // 随机字体大小
                val txtSize =
                    TEXT_SIZE_MIN + random!!.nextInt(TEXT_SIZE_MAX - TEXT_SIZE_MIN + 1)
                // 实例化TextView
                val txt = TextView(context)
                txt.setOnClickListener(itemClickListener)
                txt.text = keyword
                txt.setTextColor(ranColor)
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize.toFloat())
                txt.setShadowLayer(1f, 1f, 1f, -0x22969697)
                txt.gravity = Gravity.CENTER

                // 获取文本长度
                val paint: Paint = txt.paint
                val strWidth =
                    Math.ceil(paint.measureText(keyword).toDouble()).toInt()
                xy[IDX_TXT_LENGTH] = strWidth
                // 第一次修正:修正x坐标
                if (xy[IDX_X] + strWidth > width!! - (xItem shr 1)) {
                    val baseX = width!! - strWidth
                    // 减少文本右边缘一样的概率
                    xy[IDX_X] = baseX - xItem + random!!.nextInt(xItem shr 1)
                } else if (xy[IDX_X] == 0) {
                    // 减少文本左边缘一样的概率
                    xy[IDX_X] =
                        Math.max(random!!.nextInt(xItem), xItem / 3)
                }
                xy[IDX_DIS_Y] =
                    Math.abs(xy[IDX_Y] - yCenter)
                txt.tag = xy
                if (xy[IDX_Y] > yCenter) {
                    listTxtBottom.add(txt)
                } else {
                    listTxtTop.add(txt)
                }
            }
            attach2Screen(listTxtTop, xCenter, yCenter, yItem)
            attach2Screen(listTxtBottom, xCenter, yCenter, yItem)
            return true
        }
        return false
    }

    /** 修正TextView的Y坐标将将其添加到容器上。  */
    private fun attach2Screen(
        listTxt: LinkedList<TextView>,
        xCenter: Int,
        yCenter: Int,
        yItem: Int
    ) {
        val size = listTxt.size
        sortXYList(listTxt, size)
        for (i in 0 until size) {
            val txt = listTxt[i]
            val iXY = txt.tag as IntArray
            // 第二次修正:修正y坐标
            val yDistance = iXY[IDX_Y] - yCenter
            // 对于最靠近中心点的，其值不会大于yItem<br/>
            // 对于可以一路下降到中心点的，则该值也是其应调整的大小<br/>
            var yMove = Math.abs(yDistance)
            inner@ for (k in i - 1 downTo 0) {
                val kXY = listTxt[k].tag as IntArray
                val startX = kXY[IDX_X]
                val endX = startX + kXY[IDX_TXT_LENGTH]
                // y轴以中心点为分隔线，在同一侧
                if (yDistance * (kXY[IDX_Y] - yCenter) > 0) {
                    if (isXMixed(
                            startX,
                            endX,
                            iXY[IDX_X],
                            iXY[IDX_X] + iXY[IDX_TXT_LENGTH]
                        )
                    ) {
                        val tmpMove =
                            Math.abs(iXY[IDX_Y] - kXY[IDX_Y])
                        if (tmpMove > yItem) {
                            yMove = tmpMove
                        } else if (yMove > 0) {
                            // 取消默认值。
                            yMove = 0
                        }
                        break@inner
                    }
                }
            }
            if (yMove > yItem) {
                val maxMove = yMove - yItem
                val randomMove = random!!.nextInt(maxMove)
                val realMove =
                    Math.max(randomMove, maxMove shr 1) * yDistance / Math.abs(
                        yDistance
                    )
                iXY[IDX_Y] = iXY[IDX_Y] - realMove
                iXY[IDX_DIS_Y] =
                    Math.abs(iXY[IDX_Y] - yCenter)
                // 已经调整过前i个需要再次排序
                sortXYList(listTxt, i + 1)
            }
            val layParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            layParams.gravity = Gravity.LEFT or Gravity.TOP
            layParams.leftMargin = iXY[IDX_X]
            layParams.topMargin = iXY[IDX_Y]
            addView(txt, layParams)
            // 动画
            val animSet: AnimationSet = getAnimationSet(iXY, xCenter, yCenter, txtAnimInType)!!
            txt.startAnimation(animSet)
        }
    }

    fun getAnimationSet(
        xy: IntArray,
        xCenter: Int,
        yCenter: Int,
        type: Int
    ): AnimationSet? {
        val animSet = AnimationSet(true)
        animSet.interpolator = interpolator
        if (type == OUTSIDE_TO_LOCATION) {
            animSet.addAnimation(animAlpha2Opaque)
            animSet.addAnimation(animScaleLarge2Normal)
            val translate = TranslateAnimation(
                (xy[IDX_X] + (xy[IDX_TXT_LENGTH] shr 1) - xCenter shl 1).toFloat(),
                0f,
                (xy[IDX_Y] - yCenter shl 1).toFloat(),
                0f
            )
            animSet.addAnimation(translate)
        } else if (type == LOCATION_TO_OUTSIDE) {
            animSet.addAnimation(animAlpha2Transparent)
            animSet.addAnimation(animScaleNormal2Large)
            val translate = TranslateAnimation(
                0f,
                (xy[IDX_X] + (xy[IDX_TXT_LENGTH] shr 1) - xCenter shl 1).toFloat(),
                0f,
                (xy[IDX_Y] - yCenter shl 1).toFloat()
            )
            animSet.addAnimation(translate)
        } else if (type == LOCATION_TO_CENTER) {
            animSet.addAnimation(animAlpha2Transparent)
            animSet.addAnimation(animScaleNormal2Zero)
            val translate = TranslateAnimation(
                0f,
                (-xy[IDX_X] + xCenter).toFloat(),
                0f,
                (-xy[IDX_Y] + yCenter).toFloat()
            )
            animSet.addAnimation(translate)
        } else if (type == CENTER_TO_LOCATION) {
            animSet.addAnimation(animAlpha2Opaque)
            animSet.addAnimation(animScaleZero2Normal)
            val translate = TranslateAnimation(
                (-xy[IDX_X] + xCenter).toFloat(),
                0f,
                (-xy[IDX_Y] + yCenter).toFloat(),
                0f
            )
            animSet.addAnimation(translate)
        }
        animSet.duration = animDuration
        return animSet
    }

    /**
     * 根据与中心点的距离由近到远进行冒泡排序。
     *
     * @param endIdx
     * 起始位置。
     * / *@param txtArr
     * 待排序的数组。
     */
    private fun sortXYList(listTxt: LinkedList<TextView>, endIdx: Int) {
        for (i in 0 until endIdx) {
            for (k in i + 1 until endIdx) {
                if ((listTxt[k]
                        .tag as IntArray)[IDX_DIS_Y] < (listTxt[i]
                        .tag as IntArray)[IDX_DIS_Y]
                ) {
                    val iTmp = listTxt[i]
                    val kTmp = listTxt[k]
                    listTxt[i] = kTmp
                    listTxt[k] = iTmp
                }
            }
        }
    }

    /** A线段与B线段所代表的直线在X轴映射上是否有交集。  */
    private fun isXMixed(startA: Int, endA: Int, startB: Int, endB: Int): Boolean {
        var result = false
        if (startB >= startA && startB <= endA) {
            result = true
        } else if (endB >= startA && endB <= endA) {
            result = true
        } else if (startA >= startB && startA <= endB) {
            result = true
        } else if (endA >= startB && endA <= endB) {
            result = true
        }
        return result
    }

    private fun randomXY(
        ran: Random,
        listX: LinkedList<Int>,
        listY: LinkedList<Int>,
        xItem: Int
    ): IntArray? {
        val arr = IntArray(4)
        arr[IDX_X] = listX.removeAt(ran.nextInt(listX.size))
        arr[IDX_Y] = listY.removeAt(ran.nextInt(listY.size))
        return arr
    }

    override fun onGlobalLayout() {
        val tmpW = getWidth()
        val tmpH = getHeight()
        if (width != tmpW || height != tmpH) {
            width = tmpW
            height = tmpH
            show()
        }
    }
    fun getKeywords(): Vector<String>? {
        return vecKeywords
    }

    fun rubKeywords() {
        vecKeywords!!.clear()
    }

    /** 直接清除所有的TextView。在清除之前不会显示动画。  */
    fun rubAllViews() {
        removeAllViews()
    }

    fun setOnItemClickListener(listener: OnClickListener) {
        itemClickListener = listener
    }

}