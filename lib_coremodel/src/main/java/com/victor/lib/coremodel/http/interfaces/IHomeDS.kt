package com.victor.lib.coremodel.http.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.data.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IHomeDS
 * Author: Victor
 * Date: 2020/8/5 上午 11:05
 * Description: 
 * -----------------------------------------------------------------
 */
interface IHomeDS {

    val homeBannerData: LiveData<HttpResult<BaseReq<List<HomeBannerInfo>>>>
    suspend fun fetchHomeBanner()

    val homeSquareData: LiveData<HttpResult<BaseReq<ListData<HomeSquareInfo>>>>
    suspend fun fetchHomeSquare(page: Int)

    val hotKeyData: LiveData<HttpResult<BaseReq<List<HotKeyInfo>>>>
    suspend fun fetchHotKey()

    val meiziData: LiveData<HttpResult<BaseReq<Nothing>>>
    suspend fun fetchMeizi()

    val queryData: LiveData<HttpResult<BaseReq<ListData<HomeSquareInfo>>>>
    suspend fun fetchQuery(page: Int,key: String?)

}