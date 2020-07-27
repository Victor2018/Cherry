package com.victor.cherry.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.BannerRes
import com.victor.lib.coremodel.entity.GankRes
import com.victor.module.home.data.HomeDataSource
import com.victor.module.home.interfaces.IHomeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeViewModel
 * Author: Victor
 * Date: 2020/7/6 下午 02:40
 * Description: Showcases different patterns using the liveData coroutines builder.
 * -----------------------------------------------------------------
 */
class HomeViewModel(
    private val dataSource: IHomeDataSource
) : ViewModel() {

    // Exposed LiveData from a function that returns a LiveData generated with a liveData builder
    val currentTime = dataSource.getCurrentTime()

    // Coroutines inside a transformation
    val currentTimeTransformed = currentTime.switchMap {
        // timeStampToTime is a suspend function so we need to call it from a coroutine.
        liveData { emit(timeStampToTime(it)) }
    }

    // Exposed liveData that emits and single value and subsequent values from another source.
    val currentWeather: LiveData<String> = liveData {
        emit(LOADING_STRING)
        emitSource(dataSource.fetchWeather())
    }

    // Exposed cached value in the data source that can be updated later on
    val cachedValue = dataSource.cachedData

    val gankData: LiveData<GankRes> = liveData {
        emitSource(dataSource.fetchGankData())
    }
    val bannerData: LiveData<BannerRes> = liveData {
        emitSource(dataSource.fetchBannerData())
    }
        // Called when the user clicks on the "FETCH NEW DATA" button. Updates value in data source.
        fun onRefresh() {
            // Launch a coroutine that reads from a remote data source and updates cache
            viewModelScope.launch {
                dataSource.fetchNewData()
            }
        }

        // Simulates a long-running computation in a background thread
        private suspend fun timeStampToTime(timestamp: Long): String {
            delay(500)  // Simulate long operation
            val date = Date(timestamp)
            return date.toString()
        }

        companion object {
        // Real apps would use a wrapper on the result type to handle this.
        const val LOADING_STRING = "Loading..."
    }
}


/**
 * Factory for [LiveDataViewModel].
 */
object LiveDataVMFactory : ViewModelProvider.Factory {

    private val homeDataSource = HomeDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(homeDataSource) as T
    }
}
