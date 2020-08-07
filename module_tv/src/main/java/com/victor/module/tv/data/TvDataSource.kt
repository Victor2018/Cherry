package com.victor.module.tv.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.common.util.AssetsJsonReaderUtil
import com.victor.lib.common.util.JsonUtils
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

    private val _tvData = MutableLiveData(ChannelRes())
    override val tvData: LiveData<ChannelRes> = _tvData

    override suspend fun fetchTvData() = withContext(Dispatchers.Main) {
        _tvData.value = tvDataFetch()
    }

    private suspend fun tvDataFetch(): ChannelRes = withContext(ioDispatcher) {
        JsonUtils.parseObject(AssetsJsonReaderUtil.getJsonString("channels.json")!!,ChannelRes::class.java)!!
    }
}