package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.datasource.GirlsDataSource
import com.victor.lib.coremodel.http.interfaces.IGirlsDataSource
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.http.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsViewModel
 * Author: Victor
 * Date: 2020/7/8 下午 05:20
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
object GirlsLiveDataVMFactory : ViewModelProvider.Factory {

    private val girlsDataSource = GirlsDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GirlsViewModel(girlsDataSource) as T
    }
}
