package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.data.*
import com.victor.lib.coremodel.http.interfaces.ISearchGankDataSource
import com.victor.lib.coremodel.http.locator.NetServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:06
 * Description: A source of data for [LiveDataViewModel], showcasing different LiveData + coroutines patterns.
 * -----------------------------------------------------------------
 */
class SearchGankDataSource(private val ioDispatcher: CoroutineDispatcher): ISearchGankDataSource {

    override fun fetchHotKey(): LiveData<HotKeyRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.HOT_KEY).getHotKey())
    }

    // Cache of a data point that is exposed to VM
    override val searchGankData = MutableLiveData(GankDetailEntity())

    override suspend fun searchGank(key: String?, page: Int) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            searchGankData.value = gankDataFetch(key,page, NETWORK_PAGE_SIZE)
        }
    }

    private suspend fun gankDataFetch(key: String?, page: Int, count: Int): GankDetailEntity = withContext(ioDispatcher) {
        ServiceLocator.instance().getRequestApi(RepositoryType.SEARCH_GANK).searchGank(key,page,count)
    }


}