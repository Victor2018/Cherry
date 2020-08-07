package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.interfaces.IGankCategoryDataSource
import com.victor.lib.coremodel.http.locator.NetServiceLocator
import com.victor.lib.coremodel.http.locator.ServiceLocator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankCategoryDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:05
 * Description: 
 * -----------------------------------------------------------------
 */
class GankCategoryDataSource(private val ioDispatcher: CoroutineDispatcher):
    IGankCategoryDataSource {

    private val _gankData = MutableLiveData(GankRes())
    override val gankData: LiveData<GankRes> = _gankData

    override suspend fun fetchGankData() = withContext(Dispatchers.Main) {
        _gankData.value = gankDataFetch()
    }


    private val _girlData = MutableLiveData(GankDetailEntity())
    override val girlData: LiveData<GankDetailEntity> = _girlData

    override suspend fun fetchGirlData() = withContext(Dispatchers.Main) {
        _girlData.value = girlDataFetch()
    }


    private suspend fun gankDataFetch(): GankRes = withContext(ioDispatcher) {
        ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGank()
    }

    private suspend fun girlDataFetch(): GankDetailEntity = withContext(ioDispatcher) {
        ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGirl()
    }

}