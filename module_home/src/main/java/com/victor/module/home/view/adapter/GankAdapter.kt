package com.victor.module.home.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.module.home.view.holder.GankViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankAdapter
 * Author: Victor
 * Date: 2020/7/14 下午 06:31
 * Description: 
 * -----------------------------------------------------------------
 */
class GankAdapter(context: Context?): PagingDataAdapter<GankDetailInfo, GankViewHolder>(POST_COMPARATOR) {
    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }
    override fun onBindViewHolder(holder: GankViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: GankViewHolder,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GankViewHolder {
        return GankViewHolder.create(parent,fontStyle)
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