package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.entity.WeChatRes
import com.victor.lib.coremodel.http.interfaces.IWeChatDataSource
import com.victor.lib.coremodel.http.locator.NetServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:48
 * Description: 
 * -----------------------------------------------------------------
 */
class WeChatDataSource(private val ioDispatcher: CoroutineDispatcher): IWeChatDataSource {
    override fun fetchWeChatData(): LiveData<WeChatRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.WAN_ANDROID).getWeChat())
    }
}