package com.victor.module.tv.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.ChannelInfo
import com.victor.lib.coremodel.data.HomeSquareInfo
import com.victor.module.tv.R
import com.victor.module.tv.view.holder.TvCellContentViewHolder
import kotlinx.android.synthetic.main.rv_tv_channel_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvCellAdapter
 * Author: Victor
 * Date: 2020/7/24 下午 04:15
 * Description: 
 * -----------------------------------------------------------------
 */
class TvCellAdapter (context: Context, listener: AdapterView.OnItemClickListener, var parentPosition: Int) :
    BaseRecycleAdapter<ChannelInfo, RecyclerView.ViewHolder>(context, listener) {

    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: ChannelInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TvCellContentViewHolder(inflate(R.layout.rv_tv_channel_cell, parent),parentPosition)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: ChannelInfo?, position: Int) {
        val contentViewHolder = viewHolder as TvCellContentViewHolder
        contentViewHolder.itemView.mTvChannelName.typeface = fontStyle

        contentViewHolder.itemView.mTvChannelName.text = data?.channel_name

        if (TextUtils.isEmpty(data?.icon)) {
            ImageUtils.instance.loadImage(context,contentViewHolder.itemView.mIvTvLogo,
                R.drawable.ic_baseline_live_tv_24dp)
        } else {
            ImageUtils.instance.loadImage(context,contentViewHolder.itemView.mIvTvLogo,
                data?.icon)
        }

        contentViewHolder.mOnItemClickListener = listener
    }

}