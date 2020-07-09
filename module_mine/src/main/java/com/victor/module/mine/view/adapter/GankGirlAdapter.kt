package com.victor.module.mine.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.victor.lib.coremodel.entity.GirlInfo
import com.victor.module.mine.view.holder.GankGirlViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlAdapter
 * Author: Victor
 * Date: 2020/7/8 下午 05:24
 * Description: A simple adapter implementation that shows Reddit posts.
 * -----------------------------------------------------------------
 */
class GankGirlAdapter
    : PagingDataAdapter<GirlInfo, GankGirlViewHolder>(POST_COMPARATOR) {

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
        return GankGirlViewHolder.create(parent)
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<GirlInfo>() {
            override fun areContentsTheSame(oldItem: GirlInfo, newItem: GirlInfo): Boolean =
                oldItem._id == newItem._id

            override fun areItemsTheSame(oldItem: GirlInfo, newItem: GirlInfo): Boolean =
                oldItem.createdAt == newItem.createdAt

            override fun getChangePayload(oldItem: GirlInfo, newItem: GirlInfo): Any? {
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

        private fun sameExceptScore(oldItem: GirlInfo, newItem: GirlInfo): Boolean {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            return oldItem.createdAt == newItem.createdAt
        }
    }
}