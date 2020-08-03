package com.victor.lib.common.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.*
import android.os.Vibrator
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.victor.lib.common.R
import java.util.regex.Matcher
import java.util.regex.Pattern


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VerticalTextView
 * Author: Victor
 * Date: 2020/8/3 上午 11:21
 * Description: 
 * -----------------------------------------------------------------
 */
@SuppressLint("AppCompatCustomView")
class VerticalTextView: TextView {
    var TAG = "VerticalTextView"

    var mContext: Context? = null
    var mScreenWidth = 0 // 屏幕宽度
    var mScreenHeight  = 0// 屏幕高度
    // attrs
    var isLeftToRight = false // 竖排方向，是否从左到右；默认从右到左
    var mLineSpacingExtra = 0f// 行距 默认 6px
    var mCharSpacingExtra = 0f// 字符间距 默认 6px
    var isUnderLineText = false// 是否需要下划线，默认false
    var mUnderLineColor = 0// 下划线颜色 默认 Color.RED
    var mUnderLineWidth = 0f// 下划线线宽 默认 1.5f
    var mUnderLineOffset = 0f// 下划线偏移 默认 3px
    var mTextHighlightColor = 0// 选中文字背景高亮颜色 默认0x60ffeb3b

    // onMeasure相关
    var mTextAreaRoughBound: IntArray? = null// 粗略计算的文本最大显示区域(包含padding)，用于view的测量和不同Gravity情况下文本的绘制
    var mLinesOffsetArray: SparseArray<Array<Float>>? = null // 记录每一行文字的X,Y偏移量
    var mLinesTextIndex: SparseArray<IntArray>? = null // 记录每一行文字开始和结束字符的index
    var mMaxTextLine = 0 // 最大行数
    var mSelectedText: String? = null // 选择的文字

    var mStartLine = 0 // 长按触摸事件 所选文字的起始行
    var mCurrentLine = 0 // 长按触摸事件 移动过程中手指所在行
    var mStartTextOffset = 0f // 长按触摸事件 所选文字开始位置的Y向偏移值
    var mCurrentTextOffset = 0f // 长按触摸事件 移动过程中所选文字结束位置的Y向偏移值
    var fontStyle: Typeface? = null


    var mOnClickListener: OnClickListener? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        val mTypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView)
        mLineSpacingExtra =
            mTypedArray.getDimension(R.styleable.VerticalTextView_lineSpacingExtra, 6f)
        mCharSpacingExtra =
            mTypedArray.getDimension(R.styleable.VerticalTextView_charSpacingExtra, 6f)
        isLeftToRight = mTypedArray.getBoolean(R.styleable.VerticalTextView_textLeftToRight, false)
        isUnderLineText = mTypedArray.getBoolean(R.styleable.VerticalTextView_underLineText, false)
        mUnderLineColor =
            mTypedArray.getColor(R.styleable.VerticalTextView_underLineColor, Color.RED)
        mUnderLineWidth = mTypedArray.getDimension(R.styleable.VerticalTextView_underLineWidth, 1.5f)
        mUnderLineOffset =
            mTypedArray.getDimension(R.styleable.VerticalTextView_underlineOffset, 3f)
        mTextHighlightColor =
            mTypedArray.getColor(R.styleable.VerticalTextView_textHeightLightColor, 0x60ffeb3b)
        mTypedArray.recycle()

        mLineSpacingExtra = Math.max(6f, mLineSpacingExtra)
        mCharSpacingExtra = Math.max(6f, mCharSpacingExtra)
        if (isUnderLineText) {
            mUnderLineWidth = Math.abs(mUnderLineWidth)
            mUnderLineOffset = Math.min(
                Math.abs(mUnderLineOffset),
                Math.abs(mLineSpacingExtra) / 2
            )
        }

        init()
    }

    private fun init() {
        val wm =
            mContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mScreenWidth = wm.defaultDisplay.width
        mScreenHeight = wm.defaultDisplay.height
        setTextIsSelectable(false)
        mLinesOffsetArray = SparseArray()
        mLinesTextIndex = SparseArray()
        mTextAreaRoughBound = intArrayOf(0, 0)
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
        typeface = fontStyle
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        if (null != l) {
            mOnClickListener = l
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // view的初始测量宽高(包含padding)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        // 粗略计算文字的最大宽度和最大高度，用于修正最后的测量宽高
        mTextAreaRoughBound = getTextRoughSize(
            if (heightSize == 0) mScreenHeight else heightSize,
            mLineSpacingExtra, mCharSpacingExtra
        )
        val measuredWidth: Int
        val measureHeight: Int

        measuredWidth = if (widthSize == 0) {
            // 当嵌套在HorizontalScrollView时，MeasureSpec.getSize(widthMeasureSpec)返回0，因此需要特殊处理
            mTextAreaRoughBound!![0]
        } else {
            if (widthMode == MeasureSpec.AT_MOST
                || widthMode == MeasureSpec.UNSPECIFIED
            ) mTextAreaRoughBound!![0] else widthSize
        }
        measureHeight = if (heightSize == 0) {
            // 当嵌套在ScrollView时，MeasureSpec.getSize(widthMeasureSpec)返回0，因此需要特殊处理
            mScreenHeight
        } else {
            if (heightMode == MeasureSpec.AT_MOST
                || heightMode == MeasureSpec.UNSPECIFIED
            ) mTextAreaRoughBound!![1] else heightSize
        }
        setMeasuredDimension(measuredWidth, measureHeight)
    }

    /**
     * 粗略计算文本的宽度和高度(包含padding)，用于修正最后的测量宽高
     *
     * @param oriHeightSize    初始测量高度 必须大于0。当等于0时，用屏幕高度代替
     * @param lineSpacingExtra
     * @param charSpacingExtra
     * @return int[textWidth, textHeight]
     */
    private fun getTextRoughSize(
        oriHeightSize: Int, lineSpacingExtra: Float,
        charSpacingExtra: Float
    ): IntArray {

        // 将文本用换行符分隔，计算粗略的行数
        val subTextStr =
            text.toString().split("\n".toRegex()).toTypedArray()
        var textLines = 0
        // 用于计算最大高度的目标子段落
        var targetSubPara = ""
        var tempLines = 1
        var tempLength = 0f
        // 计算每个段落的行数，然后累加
        for (aSubTextStr in subTextStr) {
            // 段落的粗略长度(字符间距也要考虑进去)
            val subParagraphLength =
                aSubTextStr.length * (textSize + charSpacingExtra)
            // 段落长度除以初始测量高度，得到粗略行数
            var subLines = Math.ceil(
                (subParagraphLength
                        / Math.abs(oriHeightSize - paddingTop - paddingBottom)).toDouble()
            ).toInt()
            if (subLines == 0) subLines = 1
            textLines += subLines
            // 如果所有子段落的行数都为1,则最大高度为长度最长的子段落长度；否则最大高度为oriHeightSize；
            if (subLines == 1 && tempLines == 1) {
                if (subParagraphLength > tempLength) {
                    tempLength = subParagraphLength
                    targetSubPara = aSubTextStr
                }
            }
            tempLines = subLines
        }
        // 计算文本粗略高度，包括padding
        var textHeight = paddingTop + paddingBottom
        if (textLines > subTextStr.size) textHeight = oriHeightSize else {
            // 计算targetSubPara长度作为高度
            for (i in 0 until targetSubPara.length) {
                val char_i = text.toString()[i].toString()
                // 区别标点符号 和 文字
                if (isUnicodeSymbol(char_i)) {
                    textHeight += (1.4f * getCharHeight(
                        char_i,
                        getTextPaint()
                    ) + charSpacingExtra.toInt()).toInt()
                } else {
                    textHeight += (textSize + charSpacingExtra.toInt()).toInt()
                }
            }
        }
        // 计算文本的粗略宽度，包括padding，
        val textWidth = paddingLeft + paddingRight +
                ((textLines + 1) * textSize + lineSpacingExtra * (textLines - 1)).toInt()
        return intArrayOf(textWidth, textHeight)
    }

    override fun onDraw(canvas: Canvas) {
        // 绘制竖排文字
        drawVerticalText(canvas, mLineSpacingExtra, mCharSpacingExtra, isLeftToRight)
        // 绘制下划线
        drawTextUnderline(canvas, isLeftToRight, mUnderLineOffset, mCharSpacingExtra)
    }

    /**
     * 绘制竖排文字
     *
     * @param canvas
     * @param lineSpacingExtra 行距
     * @param charSpacingExtra 字符间距
     * @param isLeftToRight    文字方向
     */
    private fun drawVerticalText(
        canvas: Canvas, lineSpacingExtra: Float,
        charSpacingExtra: Float, isLeftToRight: Boolean
    ) {
        // 文字画笔
        val textPaint = getTextPaint()
        val textStrLength = text.length
        if (textStrLength == 0) return
        // 每次绘制时初始化参数
        mMaxTextLine = 1
        var currentLineStartIndex = 0 // 行首位置标记
        mLinesOffsetArray!!.clear()
        mLinesTextIndex!!.clear()
        val drawPadding = getDrawPadding(isLeftToRight) // 绘制文字的padding
        // 当前竖行的XY向偏移初始值
        var currentLineOffsetX =
            if (isLeftToRight) drawPadding[0].toFloat() else (width - drawPadding[2] - textSize).toFloat()
        var currentLineOffsetY = drawPadding[1] + textSize
        for (j in 0 until textStrLength) {
            val char_j = text[j].toString()
            /* 换行条件为：
             * 1：遇到换行符；
             * 2：该竖行是否已经写满。
             *
             * 该竖行是否已经写满，判定条件为：
             * 1.y向剩余的空间已经不够填下一个文字；
             * 2.且当前要绘制的文字不是标点符号；
             * 3.或当前要绘制的文字是标点符号，但标点符号的高度大于y向剩余的空间
             * 注意：文字是从左下角开始向上绘制的
            */
            val isLineBreaks = char_j == "\n"
            val isCurrentLineFinish =
                (currentLineOffsetY > height - drawPadding[3]
                        && (!isUnicodeSymbol(char_j) || isUnicodeSymbol(char_j) &&
                        currentLineOffsetY + getCharHeight(
                    char_j,
                    textPaint
                ) > height - drawPadding[3] + textSize))
            if (isLineBreaks || isCurrentLineFinish) {
                // 记录记录偏移量,和行首行末字符的index；然后另起一行，
                mLinesOffsetArray!!.put(
                    mMaxTextLine,
                    arrayOf(currentLineOffsetX, currentLineOffsetY)
                )
                mLinesTextIndex!!.put(mMaxTextLine, intArrayOf(currentLineStartIndex, j))
                // 另起一竖行，更新偏移量
                currentLineOffsetX =
                    if (isLeftToRight) currentLineOffsetX + textSize + lineSpacingExtra else currentLineOffsetX - textSize - lineSpacingExtra
                currentLineOffsetY = drawPadding[1] + textSize
                mMaxTextLine++
            }
            // 判断是否是行首，记录行首字符位置；
            // 判断行首的条件为：currentLineOffsetY == drawPadding[1]+getTextSize()
            if (currentLineOffsetY == drawPadding[1] + textSize) {
                currentLineStartIndex = j
            }

            // 绘制第j个字符.
            if (isLineBreaks) {
                // 如果是换行符，do nothing
                //char_j = "";
                //canvas.drawText(char_j, currentLineOffsetX, currentLineOffsetY, textPaint);
            } else if (isUnicodeSymbol(char_j)) {
                // 如果是Y向需要补偿标点符号，加一个补偿 getTextSize() - getCharHeight.
                // 注意：如果该竖行第一个字符是标点符号的话，不加补偿;
                // 判断是否是第一个字符的条件为：offsetY == drawPadding[1] + getTextSize()
                var drawOffsetY = currentLineOffsetY
                if (isSymbolNeedOffset(char_j)) drawOffsetY =
                    drawOffsetY - (textSize - 1.4f * getCharHeight(char_j, textPaint))
                // 文字从左向右，标点符号靠右绘制，竖排标点除外
                var drawOffsetX = currentLineOffsetX
                if (isLeftToRight && !isVerticalSymbol(char_j)) drawOffsetX =
                    drawOffsetX + textSize / 2
                canvas.drawText(char_j, drawOffsetX, drawOffsetY, textPaint)
                currentLineOffsetY += 1.4f * getCharHeight(char_j, textPaint) + charSpacingExtra
            } else {
                canvas.drawText(char_j, currentLineOffsetX, currentLineOffsetY, textPaint)
                currentLineOffsetY += textSize + charSpacingExtra
            }

            // 最后一行的偏移量和行首行末字符的index；
            if (j == textStrLength - 1) {
                mLinesOffsetArray!!.put(
                    mMaxTextLine,
                    arrayOf(currentLineOffsetX, currentLineOffsetY)
                )
                mLinesTextIndex!!.put(
                    mMaxTextLine,
                    intArrayOf(currentLineStartIndex, textStrLength)
                )
            }
        }
    }

    /**
     * 绘制下划线
     *
     * @param canvas
     * @param isLeftToRight    文字方向
     * @param underLineOffset  下划线偏移量 >0
     * @param charSpacingExtra
     */
    private fun drawTextUnderline(
        canvas: Canvas, isLeftToRight: Boolean, underLineOffset: Float,
        charSpacingExtra: Float
    ) {
        if (!isUnderLineText || mUnderLineWidth == 0f) return

        // 下划线paint
        val underLinePaint: Paint = paint
        underLinePaint.setColor(mUnderLineColor)
        underLinePaint.setAntiAlias(true)
        underLinePaint.setStyle(Paint.Style.FILL)
        underLinePaint.setStrokeWidth(mUnderLineWidth)
        val drawPadding = getDrawPadding(isLeftToRight) // 绘制文字的padding
        for (i in 0 until mMaxTextLine) {
            // Y向开始和结束位置
            var yStart = drawPadding[1].toFloat()
            var yEnd = mLinesOffsetArray!![i + 1][1] - textSize
            // 如果end <= start 或者 该行字符为换行符，则不绘制下划线
            val lineIndex = mLinesTextIndex!![i + 1]
            val lineText =
                text.toString().substring(lineIndex[0], lineIndex[1])
            if (yEnd <= yStart || lineText == "\n") continue
            // Y向边界处理
            if (yEnd > height - drawPadding[3] - textSize) yEnd =
                height - drawPadding[3].toFloat()
            // 首行缩进处理
            val spaceNum = getLineStartSpaceNumber(lineText)
            if (spaceNum > 0) {
                yStart = yStart + (textSize + charSpacingExtra) * spaceNum
            }

            // X向；注意不同的文字方向和下划线偏移
            var xStart = mLinesOffsetArray!![i + 1][0]
            if (isLeftToRight) xStart += textSize + underLineOffset else xStart -= underLineOffset
            val xEnd = xStart
            canvas.drawLine(xStart, yStart, xEnd, yEnd, underLinePaint)
        }
    }

    /**
     * 根据文本的Gravity计算文字绘制时的padding
     *
     * @param isLeftToRight 文字阅读方向
     * @return [left, top, right, bottom]
     */
    private fun getDrawPadding(isLeftToRight: Boolean): IntArray {
        val textBoundWidth = mTextAreaRoughBound!![0]
        val textBoundHeight = mTextAreaRoughBound!![1]
        val left: Int
        val right: Int
        val top: Int
        val bottom: Int
        var gravity: Int
        if (textBoundWidth < width) {
            // 先把水平方向的gravity解析出来
            gravity = getGravity() and Gravity.HORIZONTAL_GRAVITY_MASK
            if (gravity == Gravity.CENTER || gravity == Gravity.CENTER_HORIZONTAL) {
                left = paddingLeft + (width - textBoundWidth) / 2
                right = paddingRight + (width - textBoundWidth) / 2
            } else if (gravity == Gravity.RIGHT && isLeftToRight) {
                left = paddingLeft + width - textBoundWidth
                right = paddingRight
            } else if (gravity == Gravity.LEFT && !isLeftToRight) {
                left = paddingLeft
                right = paddingRight + width - textBoundWidth
            } else {
                left =
                    if (isLeftToRight) paddingLeft else paddingLeft + width - textBoundWidth
                right =
                    if (isLeftToRight) paddingRight + width - textBoundWidth else paddingRight
            }
        } else {
            left = paddingLeft
            right = paddingRight
        }
        if (textBoundHeight < height) {
            // 先把垂直方向的gravity解析出来
            gravity = getGravity() and Gravity.VERTICAL_GRAVITY_MASK
            if (gravity == Gravity.CENTER || gravity == Gravity.CENTER_VERTICAL) {
                top = paddingTop + (height - textBoundHeight) / 2
                bottom = paddingBottom + (height - textBoundHeight) / 2
            } else if (gravity == Gravity.BOTTOM) {
                top = paddingTop + height - textBoundHeight
                bottom = paddingBottom
            } else {
                top = paddingTop
                bottom = paddingBottom + height - textBoundHeight
            }
        } else {
            top = paddingTop
            bottom = paddingBottom
        }
        return intArrayOf(left, top, right, bottom)
    }


    /**
     * 文字画笔
     *
     * @return
     */
    private fun getTextPaint(): TextPaint {
        // 文字画笔
        val textPaint = paint
        textPaint.color = currentTextColor
        return textPaint
    }

    /**
     * 计算首行缩进的空格数
     *
     * @param lineText
     * @return
     */
    private fun getLineStartSpaceNumber(lineText: String): Int {
        return if (lineText.startsWith("    ")) {
            4
        } else if (lineText.startsWith("　　　") || lineText.startsWith("   ")) {
            3
        } else if (lineText.startsWith("　　") || lineText.startsWith("  ")) {
            2
        } else if (lineText.startsWith("　") || lineText.startsWith(" ")) {
            1
        } else 0
    }

    /**
     * 获取一个字符的高度
     *
     * @param target_char
     * @param paint
     * @return
     */
    private fun getCharHeight(target_char: String, paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds(target_char, 0, 1, rect)
        return rect.height()
    }

    /**
     * 获取一个字符的宽度
     *
     * @param target_char
     * @param paint
     * @return
     */
    private fun getCharWidth(target_char: String, paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds(target_char, 0, 1, rect)
        return rect.width()
    }

    /**
     * 判断是否是标点符号
     * - - —— = + ~ 这几个不做判断
     *
     * @param str
     * @return
     */
    private fun isUnicodeSymbol(str: String): Boolean {
        val regex =
            ".*[_\"`!@#$%^&*()|{}':;,\\[\\].<>/?！￥…（）【】‘’；：”“。，、？︵ ︷︿︹︽﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼]$+.*"
        val m: Matcher = Pattern.compile(regex).matcher(str)
        return m.matches()
    }

    /**
     * 需要补偿的标点符号
     * - - —— = + ~ 这几个不做补偿
     *
     * @param str
     * @return
     */
    private fun isSymbolNeedOffset(str: String): Boolean {
        val regex = ".*[_!@#$%&()|{}:;,\\[\\].<>/?！￥…（）【】；：。，、？︵ ︷︿︹︽﹁﹃︻]$+.*"
        val m: Matcher = Pattern.compile(regex).matcher(str)
        return m.matches()
    }

    /**
     * 是否是竖排标点符号
     *
     * @param str
     * @return
     */
    private fun isVerticalSymbol(str: String): Boolean {
        val regex = ".*[︵ ︷︿︹︽﹁﹃︻︶︸﹀︺︾ˉ﹂﹄︼|]$+.*"
        val m: Matcher = Pattern.compile(regex).matcher(str)
        return m.matches()
    }


}