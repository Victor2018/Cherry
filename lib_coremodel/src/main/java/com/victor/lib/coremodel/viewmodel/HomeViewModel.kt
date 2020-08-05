package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.BannerRes
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.HotKeyRes
import com.victor.lib.coremodel.http.datasource.HomeDataSource
import com.victor.lib.coremodel.http.interfaces.IHomeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeViewModel
 * Author: Victor
 * Date: 2020/8/5 上午 11:08
 * Description: Showcases different patterns using the liveData coroutines builder.
 * -----------------------------------------------------------------
 */
class HomeViewModel(private val dataSource: IHomeDataSource) : ViewModel() {

    val gankDetailValue = dataSource.gankDetailData

    fun searchGankDetail(type: String?,page: Int) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchGankDetail(type,page)
        }
    }
    val bannerData: LiveData<BannerRes> = liveData {
        emitSource(dataSource.fetchBannerData())
    }

}


/**
 * Factory for [LiveDataViewModel].
 */
object HomeLiveDataVMFactory : ViewModelProvider.Factory {

    private val homeDataSource = HomeDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(homeDataSource) as T
    }
}
