package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.data.GankDetailEntity
import com.victor.lib.coremodel.data.GankRes
import com.victor.lib.coremodel.http.datasource.GankCategoryDataSource
import com.victor.lib.coremodel.http.interfaces.IGankCategoryDataSource
import kotlinx.coroutines.Dispatchers

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankCategoryViewModel
 * Author: Victor
 * Date: 2020/8/5 上午 11:07
 * Description: 
 * -----------------------------------------------------------------
 */
class GankCategoryViewModel(private val dataSource: IGankCategoryDataSource) : ViewModel() {
    val gankData: LiveData<GankRes> = liveData {
        emitSource(dataSource.fetchGankData())
    }
    val girlData: LiveData<GankDetailEntity> = liveData {
        emitSource(dataSource.fetchGirlData())
    }
}
