package com.victor.module.wechat.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.victor.lib.coremodel.entity.ArticleInfo
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.wechat.view.holder.ArticleViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArticleAdapter
 * Author: Victor
 * Date: 2020/7/23 上午 11:00
 * Description: 
 * -----------------------------------------------------------------
 */
class ArticleAdapter : PagingDataAdapter<ArticleInfo, ArticleViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ArticleInfo>() {
            override fun areContentsTheSame(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean =
                oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean =
                oldItem.publishTime == newItem.publishTime

            override fun getChangePayload(oldItem: ArticleInfo, newItem: ArticleInfo): Any? {
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

        private fun sameExceptScore(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            return oldItem.publishTime == newItem.publishTime
        }
    }
}