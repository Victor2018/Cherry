package com.victor.module.tv.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.Category
import com.victor.module.tv.R
import kotlinx.android.synthetic.main.rv_tv_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvAdapter
 * Author: Victor
 * Date: 2020/7/24 下午 04:04
 * Description: 
 * -----------------------------------------------------------------
 */
class TvAdapter (context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<Category, RecyclerView.ViewHolder>(context, listener) {
    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: Category, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_tv_cell ,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: Category, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mTvCategoryTitle.typeface = fontStyle

        contentViewHolder.itemView.mTvCategoryTitle.text = data.channel_category

        contentViewHolder.itemView.recyclerView.layoutManager = LinearLayoutManager(
            contentViewHolder.itemView.context,
            LinearLayoutManager.HORIZONTAL,false)
        contentViewHolder.itemView.recyclerView.setOnFlingListener(null)
        LinearSnapHelper().attachToRecyclerView(contentViewHolder.itemView.recyclerView)

        var cellAdapter = TvCellAdapter(mContext!!,mOnItemClickListener!!,position)
        cellAdapter.add(data.channels)

        contentViewHolder.itemView.recyclerView.adapter = cellAdapter

        //解决父view 垂直recyclerView与CoordinatorLayout 联动问题
        //此横向RecyclerView的父View是纵向RecyclerView，而RecyclerView只实现了NestedScrollingChild，
        // 无法像CoordinatorLayout一样响应。所以要关闭横向RecyclerView的嵌套滑动功能，
        // 让横向RecyclerView如同其他嵌入纵向RecyclerView的view一样，触发折叠。
        //链接：简书https://www.jianshu.com/p/ddbe6a4095d3
        contentViewHolder.itemView.recyclerView.setNestedScrollingEnabled(false);

    }
}