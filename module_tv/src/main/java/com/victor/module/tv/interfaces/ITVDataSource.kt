package com.victor.module.tv.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.module.tv.data.ChannelCategory
import com.victor.module.tv.data.ChannelRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ITVDataSource
 * Author: Victor
 * Date: 2020/7/24 上午 11:30
 * Description: 
 * -----------------------------------------------------------------
 */
interface ITVDataSource {
    val tvData: LiveData<ChannelRes>
    suspend fun fetchTvData()
}