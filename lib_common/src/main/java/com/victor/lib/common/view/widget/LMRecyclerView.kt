package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LMRecyclerView.java
 * Author: Victor
 * Date: 2019/11/11 11:09
 * Description:
 * -----------------------------------------------------------------
 */
class LMRecyclerView: RecyclerView {
    private val TAG = "LMRecyclerView"

    private var isScrollingToBottom = true
    val LINEAR = 0

    val GRID = 1
    val STAGGERED_GRID = 2
    //标识RecyclerView的LayoutManager是哪种
    protected var layoutManagerType: Int = 0

    // 瀑布流的最后一个的位置
    protected lateinit var lastPositions: IntArray
    // 最后一个的位置
    protected var lastVisibleItem: Int = 0
    //第一个的位置
    protected var firstVisibleItem: Int = 0

    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var mOnScrollTopListener: OnScrollTopListener? = null
    private var hasMore = true
    private var headerCount = 1
    var sendScrollEvent = true//是否发送滚动事件

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    override fun onScrolled(dx: Int, dy: Int) {
//        Log.d(TAG, "onScrolled()......")
        isScrollingToBottom = dy > 0
        val layoutManager = layoutManager
        if (layoutManager is LinearLayoutManager) {
            layoutManagerType = LINEAR
        } else if (layoutManager is GridLayoutManager) {
            layoutManagerType = GRID
        } else if (layoutManager is StaggeredGridLayoutManager) {
            layoutManagerType = STAGGERED_GRID
        } else {
            throw RuntimeException(
                "Unsupported LayoutManager used. Valid ones are " +
                        "LinearLayoutManager, GridLayoutManager and " +
                        "StaggeredGridLayoutManager")
        }

//        Log.d(TAG, "onScrolled()......layoutManagerType = $layoutManagerType")
        when (layoutManagerType) {
            LINEAR -> {
                lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                firstVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
            GRID -> {
                lastVisibleItem = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                firstVisibleItem = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            }
            STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItem = findMax(lastPositions)

                staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)
                firstVisibleItem = findMin(lastPositions)
            }
        }
    }

    override fun onScrollStateChanged(screenState: Int) {
//        Log.d(TAG, "onScrollStateChanged()......screenState = $screenState")
        if (screenState == SCROLL_STATE_IDLE) {
            if (sendScrollEvent) {
//                LiveDataBus.sendMulti(Constant.Action.COLLAPSE_INPUT_PANEL)
            }
            val totalItemCount = layoutManager?.itemCount ?: 0
//            Log.e(TAG, "firstVisibleItem------>$firstVisibleItem")
//            Log.e(TAG, "lastVisibleItem------>$lastVisibleItem")
//            Log.e(TAG, "headerCount------>$headerCount")
//            Log.e(TAG, "hasMore------>$hasMore")
//            Log.e(TAG, "totalItemCount------->$totalItemCount")

            if (lastVisibleItem + 1 + headerCount >= totalItemCount && hasMore) {
                Log.d(TAG, "LOAD MORE DATA......")
//                LiveDataBus.sendMulti(Constant.Action.LV_SCROLL_BOTTOM)
                mOnLoadMoreListener?.OnLoadMore()//回调加载更多监听
            }
            if (firstVisibleItem == 0) {
                mOnScrollTopListener?.OnScrollTop()
            }
        }
    }

    /**
     * 需要顶部半透明渐变效果的在layout中增加以下两个属性
     * android:requiresFadingEdge="vertical"
     * android:fadingEdgeLength="@dimen/dp_50"
     */
    override fun getTopFadingEdgeStrength(): Float {  //顶部走系统的，不做修改
        return super.getTopFadingEdgeStrength()
    }

    override fun getBottomFadingEdgeStrength(): Float { //底部全透明
        return 0f
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    private fun findMin(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value < max) {
                max = value
            }
        }
        return max
    }

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        headerCount = if (hasMore) 1 else 0
    }

    interface OnLoadMoreListener {
        fun OnLoadMore()
    }

    interface OnScrollTopListener {
        fun OnScrollTop()
    }

    fun setLoadMoreListener(listener: OnLoadMoreListener) {
        mOnLoadMoreListener = listener
    }

    fun setOnScrollTopListener(listener: OnScrollTopListener) {
        mOnScrollTopListener = listener
    }

    fun isScrollTop (): Boolean {
        return firstVisibleItem == 0
    }

    fun isScrollBottom (): Boolean {
        var totalItemCount = layoutManager?.itemCount ?: 0
        return lastVisibleItem + 1 + headerCount >= totalItemCount
    }


}