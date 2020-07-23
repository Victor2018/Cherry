package com.victor.module.wechat.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.victor.lib.common.view.holder.NetworkStateItemViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArticleLoadStateAdapter
 * Author: Victor
 * Date: 2020/7/23 上午 11:08
 * Description: 
 * -----------------------------------------------------------------
 */
class ArticleLoadStateAdapter(
    private val adapter: ArticleAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(
            parent
        ) { adapter.retry() }
    }
}