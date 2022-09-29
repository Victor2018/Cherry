package com.victor.lib.coremodel.http.service

import com.victor.lib.coremodel.data.*
import com.victor.lib.coremodel.http.api.HomeApi
import org.victor.http.lib.adapter.NetworkResponse
import retrofit2.http.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeApiService
 * Author: Victor
 * Date: 2022/9/29 12:14
 * Description: 
 * -----------------------------------------------------------------
 */

interface HomeApiService {

    @GET(HomeApi.BANNER)
    suspend fun fetchHomeBanner(): NetworkResponse<BaseReq<List<HomeBannerInfo>>, HttpError>

    @GET(HomeApi.SQUARE)
    suspend fun fetchHomeSquare(
        @Path ("page") page: Int
    ): NetworkResponse<BaseReq<ListData<HomeSquareInfo>>, HttpError>

    @GET(HomeApi.HOT_KEY)
    suspend fun fetchHotKey(): NetworkResponse<BaseReq<List<HotKeyInfo>>, HttpError>

    @POST(HomeApi.QUERY)
    @FormUrlEncoded
    suspend fun query(@Path("page") page: Int,
                      @Field("k") key: String?)
    : NetworkResponse<BaseReq<ListData<HomeSquareInfo>>, HttpError>

    @GET(HomeApi.MEIZI)
    suspend fun fetchMeizi(): NetworkResponse<BaseReq<Nothing>, HttpError>

}