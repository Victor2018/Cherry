package com.victor.module.mine.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.victor.lib.common.view.holder.NetworkStateItemViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlLoadStateAdapter
 * Author: Victor
 * Date: 2020/7/14 下午 06:42
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlLoadStateAdapter  (
    private val adapter: GankGirlAdapter
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