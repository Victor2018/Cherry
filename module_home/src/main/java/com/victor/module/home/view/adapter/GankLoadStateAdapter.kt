package com.victor.module.home.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.victor.lib.common.view.holder.NetworkStateItemViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankLoadStateAdapter
 * Author: Victor
 * Date: 2020/7/14 下午 06:36
 * Description: 
 * -----------------------------------------------------------------
 */
class GankLoadStateAdapter (
    private val adapter: GankAdapter
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