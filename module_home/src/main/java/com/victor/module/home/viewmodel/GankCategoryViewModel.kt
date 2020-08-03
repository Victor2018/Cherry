package com.victor.cherry.viewmodel

import androidx.lifecycle.*
import com.victor.lib.coremodel.entity.BannerRes
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.HotKeyRes
import com.victor.module.home.data.GankCategoryDataSource
import com.victor.module.home.data.HomeDataSource
import com.victor.module.home.interfaces.IGankCategoryDataSource
import com.victor.module.home.interfaces.IHomeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankCategoryViewModel
 * Author: Victor
 * Date: 2020/7/6 下午 02:40
 * Description: Showcases different patterns using the liveData coroutines builder.
 * -----------------------------------------------------------------
 */
class GankCategoryViewModel(private val dataSource: IGankCategoryDataSource) : ViewModel() {

    val gankData: LiveData<GankRes> = liveData {
        emitSource(dataSource.fetchGankData())
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
