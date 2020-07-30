package com.victor.lib.common.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CharAvatarView
 * Author: Victor
 * Date: 2020/7/30 上午 10:19
 * Description: 
 * -----------------------------------------------------------------
 */
@SuppressLint("AppCompatCustomView")
class CharAvatarView: ImageView {
    val TAG = "CharAvatarView"

    // 颜色画板集
    val colors = intArrayOf(
        -0xe54364, -0xe95f7b, -0xe3bf1, -0xc63ee, -0xd1338f,
        -0xd851a0, -0x1981de, -0x2cac00, -0xcb6725, -0xd67f47,
        -0x18b3c4, -0x3fc6d5, -0x64a64a, -0x71bb53, -0x423c39,
        -0xcbb6a2, -0xd3c1b0, -0x6a5a5a, -0x807373, -0x137841,
        -0x278f53, -0x9687b, -0x645c82, -0x4b6dab, -0x4b6dab, -0x56beca
    )


    var mPaintBackground: Paint? = null
    var mPaintText: Paint? = null
    var mRect: Rect? = null

    var text: String? = null

    var charHash = 0

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,defStyle) {
        mPaintBackground = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintText = Paint(Paint.ANTI_ALIAS_FLAG)
        mRect = Rect()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val color: Int = colors.get(charHash % colors.size)
        // 画圆
        // 画圆
        mPaintBackground!!.color = color
        canvas!!.drawCircle(
            width / 2.toFloat(),
            width / 2.toFloat(),
            width / 2.toFloat(),
            mPaintBackground!!
        )
        // 写字
        // 写字
        mPaintText!!.color = Color.WHITE
        mPaintText!!.textSize = width / 2.toFloat()
        mPaintText!!.strokeWidth = 3f
        mPaintText!!.getTextBounds(text, 0, 1, mRect)
        // 垂直居中
        // 垂直居中
        val fontMetrics = mPaintText!!.fontMetricsInt
        val baseline = (measuredHeight - fontMetrics.bottom - fontMetrics.top) / 2
        // 左右居中
        // 左右居中
        mPaintText!!.textAlign = Paint.Align.CENTER
        canvas!!.drawText(text!!, width / 2.toFloat(), baseline.toFloat(), mPaintText!!)
    }

    /**
     * @param content 传入字符内容
     * 只会取内容的第一个字符,如果是字母转换成大写
     */
    fun setAvatarText(content: String?) {
        var content = content
        if (content == null) {
            content = " "
        }
        text = content.toCharArray()[0].toString()
        text = text!!.toUpperCase()
        charHash = text.hashCode()
        // 重绘
        invalidate()
    }
}