package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.WeChatRes
import com.victor.lib.coremodel.http.datasource.WeChatDataSource
import com.victor.lib.coremodel.http.interfaces.IWeChatDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatViewModel
 * Author: Victor
 * Date: 2020/8/5 上午 11:49
 * Description: 
 * -----------------------------------------------------------------
 */
class WeChatViewModel(private val dataSource: IWeChatDataSource): ViewModel() {

    val weChatDataValue = dataSource.weChatData

    fun fetchWeChat() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchWeChat()
        }
    }

    /**
     * Factory for [LiveDataViewModel].
     */
    object WechatLiveDataVMFactory : ViewModelProvider.Factory {

        private val weChatDataSource = WeChatDataSource(Dispatchers.IO)

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return WeChatViewModel(weChatDataSource) as T
        }
    }
}