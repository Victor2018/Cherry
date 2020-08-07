package com.victor.module.tv.viewmodel

import androidx.lifecycle.*
import com.victor.module.tv.interfaces.ITVDataSource
import com.victor.module.tv.data.ChannelCategory
import com.victor.module.tv.data.ChannelRes
import com.victor.module.tv.data.TvDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    val tvDataValue = dataSource.tvData

    fun fetchTvData() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchTvData()
        }
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