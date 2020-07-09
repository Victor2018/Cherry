package com.victor.module.mine.view.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.victor.module.mine.R
import com.victor.module.mine.databinding.NetworkStateItemBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NetworkStateItemViewHolder
 * Author: Victor
 * Date: 2020/7/8 下午 06:18
 * Description: 
 * -----------------------------------------------------------------
 */
class NetworkStateItemViewHolder (
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
) {
    private val binding = NetworkStateItemBinding.bind(itemView)
    private val progressBar = binding.progressBar
    private val errorMsg = binding.errorMsg
    private val retry = binding.retryButton
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bindTo(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? LoadState.Error)?.error?.message
    }
}
