package com.victor.module.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.*
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.locator.NetServiceLocator.Companion.NETWORK_PAGE_SIZE
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.module.home.interfaces.IGankCategoryDataSource
import com.victor.module.home.interfaces.IHomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankCategoryDataSource
 * Author: Victor
 * Date: 2020/7/6 下午 02:38
 * Description: A source of data for [LiveDataViewModel], showcasing different LiveData + coroutines patterns.
 * -----------------------------------------------------------------
 */
class GankCategoryDataSource(private val ioDispatcher: CoroutineDispatcher): IGankCategoryDataSource {

    override fun fetchGankData(): LiveData<GankRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGank())
    }

    override fun fetchGirlData(): LiveData<GankDetailEntity> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGirl())
    }


}

