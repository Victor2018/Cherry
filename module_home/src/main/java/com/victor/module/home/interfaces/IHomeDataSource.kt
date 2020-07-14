package com.victor.module.home.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.GankRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IHomeDataSource
 * Author: Victor
 * Date: 2020/7/8 上午 11:44
 * Description: 
 * -----------------------------------------------------------------
 */
interface IHomeDataSource {
    fun getCurrentTime(): LiveData<Long>
    fun fetchWeather(): LiveData<String>
    val cachedData: LiveData<String>
    suspend fun fetchNewData()
    fun fetchGankData(): LiveData<GankRes>
}