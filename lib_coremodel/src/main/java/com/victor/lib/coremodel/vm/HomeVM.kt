package com.victor.lib.coremodel.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.lib.coremodel.http.interfaces.IHomeDS
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeVM
 * Author: Victor
 * Date: 2022/9/29 11:55
 * Description: 
 * -----------------------------------------------------------------
 */

class HomeVM(private val dataSource: IHomeDS): ViewModel() {

    val homeBannerData = dataSource.homeBannerData
    fun fetchHomeBanner() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHomeBanner()
        }
    }

    val homeSquareData = dataSource.homeSquareData
    fun fetchHomeSquare(page: Int) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHomeSquare(page)
        }
    }

    val hotKeyData = dataSource.hotKeyData
    fun fetchHotKey() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotKey()
        }
    }

    val meiziData = dataSource.meiziData
    fun fetchMeizi() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchMeizi()
        }
    }

    val queryData = dataSource.queryData
    fun fetchQuery(page: Int,key: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchQuery(page, key)
        }
    }

}