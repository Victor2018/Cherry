package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.data.BannerRes
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.http.interfaces.IHomeDataSource
import com.victor.lib.coremodel.http.repository.GankRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
class HomeViewModel(private val dataSource: IHomeDataSource,private var repository: GankRepository) : ViewModel() {

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

    fun addFavGank(data: GankDetailInfo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addFavGank(data._id,data.type,data.category)
            }
        }
    }
    fun removeFavGank(data: GankDetailInfo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.removeFavGank(data._id,data.type)
            }
        }
    }

    fun isGankFavoreted(favId: String) = repository.isGankFavoreted(favId)

    val favGanks = repository.getFavGanks("Android")
}


/**
 * Factory for [LiveDataViewModel].
 */
//object HomeLiveDataVMFactory : ViewModelProvider.Factory {
//
//    private val homeDataSource = HomeDataSource(Dispatchers.IO)
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        return HomeViewModel(homeDataSource) as T
//    }
//}
