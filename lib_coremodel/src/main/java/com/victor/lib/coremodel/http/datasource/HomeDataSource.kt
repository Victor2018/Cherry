package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.*
import com.victor.lib.coremodel.http.interfaces.IHomeDataSource
import com.victor.lib.coremodel.http.locator.NetServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator
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
 * Date: 2020/8/5 上午 11:06
 * Description: A source of data for [LiveDataViewModel], showcasing different LiveData + coroutines patterns.
 * -----------------------------------------------------------------
 */
class HomeDataSource(private val ioDispatcher: CoroutineDispatcher): IHomeDataSource {

    override fun fetchBannerData(): LiveData<BannerRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getBanner())
    }

    // Cache of a data point that is exposed to VM
    private val _gankDetailData = MutableLiveData(GankDetailEntity())
    override val gankDetailData: LiveData<GankDetailEntity> = _gankDetailData

    override suspend fun fetchGankDetail(key: String?, page: Int) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            _gankDetailData.value = gankDetailDataFetch(key,page,
                NetServiceLocator.NETWORK_PAGE_SIZE
            )
        }
    }

    private suspend fun gankDetailDataFetch(type: String?, page: Int, count: Int): GankDetailEntity = withContext(ioDispatcher) {
        ServiceLocator.instance().getRequestApi(RepositoryType.GANK_DETAIL).getGankDetail(type,page,count)
    }


}