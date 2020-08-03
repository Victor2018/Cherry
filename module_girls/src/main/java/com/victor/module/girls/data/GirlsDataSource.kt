package com.victor.module.girls.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.locator.NetServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.module.girls.interfaces.IGirlsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsDataSource
 * Author: Victor
 * Date: 2020/8/3 下午 07:00
 * Description: 
 * -----------------------------------------------------------------
 */
class GirlsDataSource(private val ioDispatcher: CoroutineDispatcher): IGirlsDataSource {
    private val _girlsData = MutableLiveData(GankDetailEntity())
    override val girlsData: LiveData<GankDetailEntity> = _girlsData

    override suspend fun fetchGirls(page: Int) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            _girlsData.value = girlsDataFetch(page,
                NetServiceLocator.NETWORK_PAGE_SIZE
            )
        }
    }

    private suspend fun girlsDataFetch(page: Int, count: Int): GankDetailEntity = withContext(ioDispatcher) {
        ServiceLocator.instance().getRequestApi(RepositoryType.SEARCH_GANK).getFuliList(page,count)
    }
}