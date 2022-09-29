package com.victor.module.home.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.app.App
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.data.HomeSquareInfo
import com.victor.module.home.R
import com.victor.module.home.view.holder.GankContentViewHolder
import kotlinx.android.synthetic.main.rv_gank_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankAdapter
 * Author: Victor
 * Date: 2020/7/13 下午 02:23
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchGankAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<HomeSquareInfo, RecyclerView.ViewHolder>(context, listener)  {
    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeSquareInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(inflate(R.layout.rv_gank_cell, parent))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeSquareInfo?, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mTvTitle.typeface = fontStyle
        contentViewHolder.itemView.mTvTime.typeface = fontStyle

        contentViewHolder.itemView.mTvTitle.setText(
            Html.fromHtml(
                ("<a href=\""
                        + data?.link) + "\">"
                        + data?.title + "</a>" + "[" + data?.niceShareDate
                    .toString() + "]"
            )
        )

        contentViewHolder.itemView.mTvTime.text = data?.shareUser
        contentViewHolder.mOnItemClickListener = listener
    }
}