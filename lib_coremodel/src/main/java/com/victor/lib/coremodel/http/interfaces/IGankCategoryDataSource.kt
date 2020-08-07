package com.victor.lib.coremodel.http.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IGankCategoryDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:05
 * Description: 
 * -----------------------------------------------------------------
 */
interface IGankCategoryDataSource {
    val gankData: LiveData<GankRes>
    suspend fun fetchGankData()

    val girlData: LiveData<GankDetailEntity>
    suspend fun fetchGirlData()
}