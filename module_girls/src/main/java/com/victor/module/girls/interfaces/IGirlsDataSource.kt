package com.victor.module.girls.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.GankDetailEntity

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IGirlsDataSource
 * Author: Victor
 * Date: 2020/8/3 下午 06:18
 * Description: 
 * -----------------------------------------------------------------
 */
interface IGirlsDataSource {
    val girlsData: LiveData<GankDetailEntity>
    suspend fun fetchGirls(page: Int)
}