package com.victor.lib.coremodel.http

import com.victor.lib.coremodel.http.adapter.NetworkResponseAdapterFactory
import com.victor.lib.coremodel.http.converter.FastJsonConverterFactory
import com.victor.lib.coremodel.http.interceptor.BasicParamsInterceptor
import com.victor.lib.coremodel.http.interceptor.LogInterceptor
import com.victor.lib.coremodel.util.WebConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ApiClient
 * Author: Victor
 * Date: 2021/2/24 14:19
 * Description: 使用协程进行网络请求实现的网络请求客户端
 * -----------------------------------------------------------------
 */
object ApiClient {
    const val TIME_OUT:Long = 30

    /**
     * 用于存储ApiService
     */
    private val map = mutableMapOf<Class<*>, Any>()

    private val basicParamsInterceptor by lazy {
        BasicParamsInterceptor.Builder()
//                .addHeaderParam("X-TOKEN","ff8ebca1e43feb34baf10b7b71b0ca2a98f5b2650df3b85e976639ffbbe9bacf")
//                .addQueryParam("deviceId", "12345")
            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(LogInterceptor())
            .addInterceptor(basicParamsInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WebConfig.getBaseUrl())
            .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(FastJsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    /**
     * 获得想要的 retrofit service
     * @param clazz    想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>      api service
     * @return
     */
    fun <T : Any> getApiService(apiClass: Class<T>): T {
        return getService(apiClass)
    }

    /**
     * 获取ApiService单例对象
     */
    private fun <T : Any> getService(apiClass: Class<T>): T{
        //重入锁单例避免多线程安全问题
        return if (map[apiClass] == null) {
            synchronized(ApiClient::class.java) {
                val t = retrofit.create(apiClass)
                if (map[apiClass] == null) {
                    map[apiClass] = t
                }
                t
            }
        } else {
            map[apiClass] as T
        }
    }
}