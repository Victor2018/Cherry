package com.victor.module.tv.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.common.util.AssetsJsonReaderUtil
import com.victor.lib.common.util.JsonUtils
import com.victor.lib.coremodel.entity.ChannelRes
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.locator.NetServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.module.tv.interfaces.ITVDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvDataSource
 * Author: Victor
 * Date: 2020/7/24 上午 11:31
 * Description: 
 * -----------------------------------------------------------------
 */
class TvDataSource(private val ioDispatcher: CoroutineDispatcher):
    ITVDataSource {

    override fun fetchTvData(): LiveData<ChannelRes> = liveData {
        withContext(ioDispatcher) {
            AssetsJsonReaderUtil.getJsonString("channels.json")?.let {
                JsonUtils.parseObject(it,ChannelRes::class.java)
                    ?.let { emit(it) }
            }
        }

    }
}