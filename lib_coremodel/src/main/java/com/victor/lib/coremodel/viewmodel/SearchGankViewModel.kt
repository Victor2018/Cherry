package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.HotKeyRes
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.datasource.GirlsDataSource
import com.victor.lib.coremodel.http.datasource.SearchGankDataSource
import com.victor.lib.coremodel.http.interfaces.IGirlsDataSource
import com.victor.lib.coremodel.http.interfaces.ISearchGankDataSource
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.http.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankViewModel
 * Author: Victor
 * Date: 2020/7/8 下午 05:20
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchGankViewModel(private val dataSource: ISearchGankDataSource) : ViewModel() {
    val seachGankValue = dataSource.searchGankData

    fun searchGank(key: String?,page: Int) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.searchGank(key,page)
        }
    }

    val hotKeyDataValue = dataSource.hotKeyData
    fun fetchHotKey() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotKey()
        }
    }

    /**
     * Factory for [LiveDataViewModel].
     */
    object SearchGankLiveDataVMFactory : ViewModelProvider.Factory {

        private val dataSource = SearchGankDataSource(Dispatchers.IO)

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchGankViewModel(dataSource) as T
        }
    }
}
