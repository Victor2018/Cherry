package com.victor.lib.coremodel.http.datasource

import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.data.*
import com.victor.lib.coremodel.http.interfaces.IHomeDS
import com.victor.lib.coremodel.http.service.HomeApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.victor.http.lib.ApiClient

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeDS
 * Author: Victor
 * Date: 2022/9/29 12:07
 * Description: 
 * -----------------------------------------------------------------
 */

class HomeDS: AbsDS(),IHomeDS {

    override val homeBannerData = MutableLiveData<HttpResult<BaseReq<List<HomeBannerInfo>>>>()
    override suspend fun fetchHomeBanner() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            homeBannerData.value = homeBannerReq()
        }
    }

    override val homeSquareData = MutableLiveData<HttpResult<BaseReq<ListData<HomeSquareInfo>>>>()
    override suspend fun fetchHomeSquare(page: Int) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            homeSquareData.value = homeSquareReq(page)
        }
    }

    override val hotKeyData = MutableLiveData<HttpResult<BaseReq<List<HotKeyInfo>>>>()
    override suspend fun fetchHotKey() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotKeyData.value = hotKeyReq()
        }
    }

    override val meiziData = MutableLiveData<HttpResult<BaseReq<Nothing>>>()
    override suspend fun fetchMeizi() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            meiziData.value = meiziReq()
        }
    }

    override val queryData = MutableLiveData<HttpResult<BaseReq<ListData<HomeSquareInfo>>>>()
    override suspend fun fetchQuery(page: Int,key: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            queryData.value = queryReq(page,key)
        }
    }

    private suspend fun <T> homeBannerReq(): T = withContext(Dispatchers.IO) {
        handleRespone(
            ApiClient.getApiService(HomeApiService::class.java)
                .fetchHomeBanner()) as T
    }

    private suspend fun <T> homeSquareReq(page: Int): T = withContext(Dispatchers.IO) {
        handleRespone(
            ApiClient.getApiService(HomeApiService::class.java)
                .fetchHomeSquare(page)) as T
    }

    private suspend fun <T> hotKeyReq(): T = withContext(Dispatchers.IO) {
        handleRespone(
            ApiClient.getApiService(HomeApiService::class.java)
                .fetchHotKey()) as T
    }

    private suspend fun <T> meiziReq(): T = withContext(Dispatchers.IO) {
        handleRespone(
            ApiClient.getApiService(HomeApiService::class.java)
                .fetchMeizi()) as T
    }

    private suspend fun <T> queryReq(page: Int,key: String?): T = withContext(Dispatchers.IO) {
        handleRespone(
            ApiClient.getApiService(HomeApiService::class.java)
                .query(page, key)) as T
    }

}