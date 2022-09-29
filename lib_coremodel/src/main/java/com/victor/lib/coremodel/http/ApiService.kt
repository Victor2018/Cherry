package com.victor.lib.coremodel.http

import android.util.Log
import com.alibaba.fastjson.JSON
import com.victor.lib.coremodel.data.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ApiService
 * Author: Victor
 * Date: 2020/7/8 下午 04:49
 * Description: 使用协程进行网络请求
 * -----------------------------------------------------------------
 */
interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWeChat(): WeChatRes

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getArticles(
        @Path("id") id: Int?,
        @Path("page") page: Int = 1
    ): ArticleRes

    companion object {
        const val TAG = "ApiService"
        const val WAN_ANDROID_HOST = "https://wanandroid.com/"
        const val TIME_OUT:Long = 30
        fun create(baseUrl: String): ApiService {

            val client = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }


}