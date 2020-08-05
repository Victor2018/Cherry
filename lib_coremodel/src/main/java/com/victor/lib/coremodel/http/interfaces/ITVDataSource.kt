package com.victor.lib.coremodel.http.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.ChannelRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ITVDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:28
 * Description: 
 * -----------------------------------------------------------------
 */
interface ITVDataSource {
    fun fetchTvData(): LiveData<ChannelRes>
}