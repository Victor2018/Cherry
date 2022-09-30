package com.victor.module.tv.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.data.ChannelRes
import com.victor.module.tv.interfaces.ITVDataSource
import com.victor.module.tv.data.TvDataSource
import kotlinx.coroutines.Dispatchers

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvViewModel
 * Author: Victor
 * Date: 2020/7/24 上午 11:43
 * Description: 
 * -----------------------------------------------------------------
 */
class TvViewModel(private val dataSource: ITVDataSource) : ViewModel() {

    val tvData: LiveData<ChannelRes> = liveData {
        emitSource(dataSource.fetchTvData())
    }
}
