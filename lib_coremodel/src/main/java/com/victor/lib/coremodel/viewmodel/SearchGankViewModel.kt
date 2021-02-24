package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.data.HotKeyRes
import com.victor.lib.coremodel.http.datasource.SearchGankDataSource
import com.victor.lib.coremodel.http.interfaces.ISearchGankDataSource
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

    val hotKeyData: LiveData<HotKeyRes> = liveData {
        emitSource(dataSource.fetchHotKey())
    }

}
