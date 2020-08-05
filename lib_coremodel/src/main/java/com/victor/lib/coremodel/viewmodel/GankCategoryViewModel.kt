package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
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


/**
 * Factory for [LiveDataViewModel].
 */
object GankCategoryLiveDataVMFactory : ViewModelProvider.Factory {

    private val homeDataSource = GankCategoryDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GankCategoryViewModel(homeDataSource) as T
    }
}