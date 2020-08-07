package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.http.datasource.GankCategoryDataSource
import com.victor.lib.coremodel.http.interfaces.IGankCategoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    val gankDataValue = dataSource.gankData
    fun fetchGank() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchGankData()
        }
    }

    val girlDataValue = dataSource.girlData
    fun fetchGirl() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchGirlData()
        }
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