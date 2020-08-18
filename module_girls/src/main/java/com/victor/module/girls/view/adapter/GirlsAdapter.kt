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
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<GankDetailInfo>() {
            override fun areContentsTheSame(oldItem: GankDetailInfo, newItem: GankDetailInfo): Boolean =
                oldItem._id == newItem._id

            override fun areItemsTheSame(oldItem: GankDetailInfo, newItem: GankDetailInfo): Boolean =
                oldItem.createdAt == newItem.createdAt

            override fun getChangePayload(oldItem: GankDetailInfo, newItem: GankDetailInfo): Any? {
                return if (sameExceptScore(
                        oldItem,
                        newItem
                    )
                ) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: GankDetailInfo, newItem: GankDetailInfo): Boolean {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            return oldItem.createdAt == newItem.createdAt
        }
    }
}