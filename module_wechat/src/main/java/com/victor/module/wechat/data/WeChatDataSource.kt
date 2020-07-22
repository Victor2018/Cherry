package com.victor.module.wechat.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.entity.WeChatRes
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.module.wechat.interfaces.IWeChatDataSource
import kotlinx.coroutines.CoroutineDispatcher

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatDataSource
 * Author: Victor
 * Date: 2020/7/22 上午 10:53
 * Description: 
 * -----------------------------------------------------------------
 */
class WeChatDataSource(private val ioDispatcher: CoroutineDispatcher): IWeChatDataSource {
    override fun fetchWeChatData(): LiveData<WeChatRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.WAN_ANDROID).getWeChat())
    }
}