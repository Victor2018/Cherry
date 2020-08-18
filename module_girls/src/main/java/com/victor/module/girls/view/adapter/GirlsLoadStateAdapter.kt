package com.victor.module.girls.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.victor.lib.common.view.holder.NetworkStateItemViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsLoadStateAdapter
 * Author: Victor
 * Date: 2020/8/17 下午 06:22
 * Description: 
 * -----------------------------------------------------------------
 */

class GirlsLoadStateAdapter (
    private val adapter: GirlsAdapter
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