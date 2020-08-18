package com.victor.module.girls.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.module.girls.R
import kotlinx.android.synthetic.main.rv_girls_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsViewAdapter
 * Author: Victor
 * Date: 2020/7/8 下午 05:24
 * Description: A simple adapter implementation that shows Reddit posts.
 * -----------------------------------------------------------------
 */
class GirlsViewAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<GankDetailInfo, RecyclerView.ViewHolder>(context, listener) {
    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }
    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: GankDetailInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_girls_view_cell ,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: GankDetailInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        contentViewHolder.itemView.tv_title.typeface = fontStyle
        contentViewHolder.itemView.tv_title.text = data?.desc ?: "loading"
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.iv_img,data.url)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}