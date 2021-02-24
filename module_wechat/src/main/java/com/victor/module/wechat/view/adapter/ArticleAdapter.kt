package com.victor.module.wechat.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.victor.lib.coremodel.data.ArticleInfo
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
class ArticleAdapter(context: Context?) : PagingDataAdapter<ArticleInfo, ArticleViewHolder>(POST_COMPARATOR) {
    var fontStyle: Typeface? = null
    init {
        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
    }

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
        return ArticleViewHolder.create(parent,fontStyle)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ArticleInfo>() {
            override fun areContentsTheSame(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: ArticleInfo, newItem: ArticleInfo): Boolean =
                oldItem.id == newItem.id

        }
    }
}