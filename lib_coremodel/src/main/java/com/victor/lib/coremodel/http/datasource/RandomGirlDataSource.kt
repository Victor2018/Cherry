package com.victor.lib.coremodel.http.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.data.*
import com.victor.lib.coremodel.http.interfaces.IRandomGirlDataSource
import com.victor.lib.coremodel.http.locator.ServiceLocator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:06
 * Description: A source of data for [LiveDataViewModel], showcasing different LiveData + coroutines patterns.
 * -----------------------------------------------------------------
 */
class RandomGirlDataSource(private val ioDispatcher: CoroutineDispatcher): IRandomGirlDataSource {

    // Cache of a data point that is exposed to VM
    private val _randomGirlData = MutableLiveData(GankDetailEntity())
    override val randomGirlData: LiveData<GankDetailEntity> = _randomGirlData

    override suspend fun fetchRandomGirl() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            _randomGirlData.value = RandomGirlDataFetch()
        }
    }

    private suspend fun RandomGirlDataFetch(): GankDetailEntity = withContext(ioDispatcher) {
        ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGirl()
    }



}