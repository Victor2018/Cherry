package com.victor.module.home.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.entity.GankInfo
import com.victor.lib.coremodel.entity.WeChatInfo
import com.victor.module.home.R
import kotlinx.android.synthetic.main.rv_gank_category_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankCategoryAdapter
 * Author: Victor
 * Date: 2020/8/1 下午 05:47
 * Description: 
 * -----------------------------------------------------------------
 */
class GankCategoryAdapter (context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<GankInfo, RecyclerView.ViewHolder>(context, listener) {
    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }
    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: GankInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_gank_category_cell ,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: GankInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        contentViewHolder.itemView.mTvTitle.typeface = fontStyle
        contentViewHolder.itemView.mTvTitle.text = data.desc
        contentViewHolder.itemView.mCavAvatar.setAvatarText(data.title)

        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}