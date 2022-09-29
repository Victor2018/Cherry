package com.victor.module.mine.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.GankAndFavGankInfo
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.module.mine.R
import kotlinx.android.synthetic.main.rv_fav_girl_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FavGirlsAdapter
 * Author: Victor
 * Date: 2020/8/21 下午 03:23
 * Description: 
 * -----------------------------------------------------------------
 */

class FavGirlsAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<GankAndFavGankInfo, RecyclerView.ViewHolder>(context, listener) {
    val BIG_ITEM = 3
    val NORMAL_ITEM = 4

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: GankAndFavGankInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == NORMAL_ITEM) {
            return ContentViewHolder(inflate(R.layout.rv_fav_girl_normal_cell, parent))
        }
        return ContentViewHolder(inflate(R.layout.rv_fav_girl_cell, parent))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: GankAndFavGankInfo?, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        ImageUtils.instance.loadImage(context,contentViewHolder.itemView.iv_img,data?.plant?.images?.get(0))
        contentViewHolder.mOnItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    fun getViewType(position: Int): Int {
        return if (position == 0) {
                BIG_ITEM
        } else {
            NORMAL_ITEM
        }
    }

}