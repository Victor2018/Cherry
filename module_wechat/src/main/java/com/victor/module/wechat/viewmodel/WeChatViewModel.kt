package com.victor.module.wechat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.WeChatRes
import com.victor.module.wechat.data.WeChatDataSource
import com.victor.module.wechat.interfaces.IWeChatDataSource
import kotlinx.coroutines.Dispatchers

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatViewModel
 * Author: Victor
 * Date: 2020/7/22 上午 10:57
 * Description: 
 * -----------------------------------------------------------------
 */
class WeChatViewModel(private val dataSource: IWeChatDataSource): ViewModel() {

    val weChatData: LiveData<WeChatRes> = liveData {
        emitSource(dataSource.fetchWeChatData())
    }

    /**
     * Factory for [LiveDataViewModel].
     */
    object LiveDataVMFactory : ViewModelProvider.Factory {

        private val weChatDataSource = WeChatDataSource(Dispatchers.IO)

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return WeChatViewModel(weChatDataSource) as T
        }
    }
}