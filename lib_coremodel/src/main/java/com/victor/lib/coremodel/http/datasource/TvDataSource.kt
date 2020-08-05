package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.ChannelRes
import com.victor.lib.coremodel.http.interfaces.ITVDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:31
 * Description: 
 * -----------------------------------------------------------------
 */
class TvDataSource(private val ioDispatcher: CoroutineDispatcher):
    ITVDataSource {
    override fun fetchTvData(): LiveData<ChannelRes> = liveData {
        /*AssetsJsonReaderUtil.getJsonString("channels.json")?.let {
            JsonUtils.parseObject(it,ChannelRes::class.java)
                ?.let { emit(it) }
        }*/
    }

}