package com.victor.lib.coremodel.http.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.BannerRes
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.HotKeyRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ISearchGankDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:05
 * Description: 
 * -----------------------------------------------------------------
 */
interface ISearchGankDataSource {
    fun fetchHotKey(): LiveData<HotKeyRes>
    val searchGankData: LiveData<GankDetailEntity>
    suspend fun searchGank(key: String?,page: Int)

}