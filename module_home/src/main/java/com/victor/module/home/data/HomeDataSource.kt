package com.victor.module.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.BannerRes
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.HotKeyRes
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.module.home.interfaces.IHomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeDataSource
 * Author: Victor
 * Date: 2020/7/6 下午 02:38
 * Description: A source of data for [LiveDataViewModel], showcasing different LiveData + coroutines patterns.
 * -----------------------------------------------------------------
 */
class HomeDataSource(private val ioDispatcher: CoroutineDispatcher): IHomeDataSource {

    /**
     * LiveData builder generating a value that will be transformed.
     */
    override fun getCurrentTime(): LiveData<Long> =
        liveData {
            while (true) {
                emit(System.currentTimeMillis())
                delay(1000)
            }
        }

    /**
     * emit + emitSource pattern (see ViewModel).
     */

    // Exposes a LiveData of changing weather conditions, every 2 seconds.
    private val weatherConditions = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Snowy")

    override fun fetchWeather(): LiveData<String> = liveData {
        var counter = 0
        while (true) {
            counter++
            delay(2000)

            emit(weatherConditions[counter % weatherConditions.size])
        }
    }

    /**
     * Cache + Remote data source.
     */

    // Cache of a data point that is exposed to VM
    private val _cachedData = MutableLiveData("This is old data")
    override val cachedData: LiveData<String> = _cachedData

    // Called when the cache needs to be refreshed. Must be called from coroutine.
    override suspend fun fetchNewData() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            _cachedData.value = "Fetching new data..."
            _cachedData.value = simulateNetworkDataFetch()
        }
    }

    // Fetches new data in the background. Must be called from coroutine so it's scoped correctly.
    private var counter = 0
    // Using ioDispatcher because the function simulates a long and expensive operation.
    private suspend fun simulateNetworkDataFetch(): String = withContext(ioDispatcher) {
        delay(3000)
        counter++
        "New data from request #$counter"
    }

    override fun fetchGankData(): LiveData<GankRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGank())
    }

    override fun fetchBannerData(): LiveData<BannerRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getBanner())
    }

    override fun fetchHotKey(): LiveData<HotKeyRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.HOT_KEY).getHotKey())
    }


}

