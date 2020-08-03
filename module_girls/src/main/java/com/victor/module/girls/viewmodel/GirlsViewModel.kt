package com.victor.module.girls.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.victor.module.girls.data.GirlsDataSource
import com.victor.module.girls.interfaces.IGirlsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsViewModel
 * Author: Victor
 * Date: 2020/8/3 下午 06:56
 * Description: 
 * -----------------------------------------------------------------
 */
class GirlsViewModel(private val dataSource: IGirlsDataSource): ViewModel() {
    val girlsDataValue = dataSource.girlsData

    fun fetchGirls(page: Int) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchGirls(page)
        }
    }
}

/**
 * Factory for [LiveDataViewModel].
 */
object LiveDataVMFactory : ViewModelProvider.Factory {

    private val girlsDataSource = GirlsDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GirlsViewModel(girlsDataSource) as T
    }
}