package com.victor.lib.coremodel.http.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.data.BannerRes
import com.victor.lib.coremodel.data.GankDetailEntity

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IRandomGirlDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:05
 * Description: 
 * -----------------------------------------------------------------
 */
interface IRandomGirlDataSource {
    val randomGirlData: LiveData<GankDetailEntity>
    suspend fun fetchRandomGirl()
}