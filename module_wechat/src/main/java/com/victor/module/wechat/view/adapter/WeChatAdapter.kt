package com.victor.module.wechat.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.entity.GankInfo
import com.victor.lib.coremodel.entity.WeChatInfo
import com.victor.module.wechat.R
import kotlinx.android.synthetic.main.rv_wechat_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatAdapter
 * Author: Victor
 * Date: 2020/7/22 上午 11:09
 * Description: 
 * -----------------------------------------------------------------
 */
class WeChatAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<WeChatInfo, RecyclerView.ViewHolder>(context, listener) {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: WeChatInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_wechat_cell ,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: WeChatInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        contentViewHolder.itemView.mTvTitle.text = data.name
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}