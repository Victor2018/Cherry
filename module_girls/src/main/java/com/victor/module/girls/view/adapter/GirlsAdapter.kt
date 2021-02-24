package com.victor.module.girls.view.adapter

import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.module.girls.view.holder.GankGirlViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsAdapter
 * Author: Victor
 * Date: 2020/8/17 下午 06:19
 * Description: 
 * -----------------------------------------------------------------
 */

class GirlsAdapter(var listener: AdapterView.OnItemClickListener) : PagingDataAdapter<GankDetailInfo, GankGirlViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: GankGirlViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: GankGirlViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.updateScore(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GankGirlViewHolder {
        return GankGirlViewHolder.create(parent,listener)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<GankDetailInfo>() {
            override fun areContentsTheSame(oldItem: GankDetailInfo, newItem: GankDetailInfo): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: GankDetailInfo, newItem: GankDetailInfo): Boolean =
                oldItem._id == newItem._id

        }
    }
}