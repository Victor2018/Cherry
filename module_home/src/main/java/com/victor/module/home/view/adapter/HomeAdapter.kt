package com.victor.module.home.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.entity.GankInfo
import com.victor.module.home.R
import kotlinx.android.synthetic.main.rv_home_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeAdapter
 * Author: Victor
 * Date: 2020/7/13 下午 02:23
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<GankInfo, RecyclerView.ViewHolder>(context, listener)  {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: GankInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_home_cell ,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: GankInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        contentViewHolder.itemView.mTvTitle.text = data.desc;
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvPoster, data.coverImageUrl)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}