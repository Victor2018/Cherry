package com.victor.module.tv.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.ChannelRes
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

/**
 * Factory for [LiveDataViewModel].
 */
object LiveDataVMFactory : ViewModelProvider.Factory {

    private val tvDataSource = TvDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TvViewModel(tvDataSource) as T
    }
}