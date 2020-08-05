package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.interfaces.IGankCategoryDataSource
import com.victor.lib.coremodel.http.locator.ServiceLocator
import kotlinx.coroutines.CoroutineDispatcher
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

    override fun fetchGankData(): LiveData<GankRes> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGank())
    }

    override fun fetchGirlData(): LiveData<GankDetailEntity> = liveData {
        emit(ServiceLocator.instance().getRequestApi(RepositoryType.GANK).getGirl())
    }

}