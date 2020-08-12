package com.victor.module.tv.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.ChannelRes

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
    fun fetchTvData(): LiveData<ChannelRes>
}