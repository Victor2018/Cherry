package com.victor.lib.coremodel.http.locator

import android.content.Context
import androidx.paging.PagingConfig
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.repository.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NetServiceLocator
 * Author: Victor
 * Date: 2020/7/9 上午 11:31
 * Description: default implementation of ServiceLocator that uses production endpoints.
 * -----------------------------------------------------------------
 */
class NetServiceLocator : ServiceLocator {
    private val api by lazy {
        ApiService.create(ApiService.GANK_HOST)
    }
    private val wanAndroidApi by lazy {
        ApiService.create(ApiService.WAN_ANDROID_HOST)
    }

    override fun getRepository(type: RepositoryType,context: Context): IRepository {
        when (type) {
            RepositoryType.GANK_GIRL -> {
                return GankGirlRepository(requestApi = getRequestApi(type),
                    pageConfig = getPagingConfigs(),
                db = AppDatabase.getInstance(context.applicationContext))
            }
            RepositoryType.GANK_DETAIL -> {
                return GankDetailRepository(requestApi = getRequestApi(type),pageConfig = getPagingConfigs())
            }
            RepositoryType.ARTICLE -> {
                return ArticleRepository(requestApi = getRequestApi(type),pageConfig = getPagingConfigs())
            }
            RepositoryType.SEARCH_GANK -> {
                return SearchGankRepository(requestApi = getRequestApi(type),pageConfig = getPagingConfigs())
            }
        }
        return GankDetailRepository(requestApi = getRequestApi(type),pageConfig = getPagingConfigs())
    }

    override fun getRequestApi(type: RepositoryType): ApiService {
        when (type) {
            RepositoryType.WAN_ANDROID -> {
                return wanAndroidApi
            }
            RepositoryType.ARTICLE -> {
                return wanAndroidApi
            }
            RepositoryType.HOT_KEY -> {
                return wanAndroidApi
            }
        }
        return return api
    }

    override fun getPagingConfigs(): PagingConfig {
        val pagingConfig = PagingConfig(
            // 每页显示的数据的大小
            pageSize = ServiceLocator.NETWORK_PAGE_SIZE,

            // 开启占位符
            enablePlaceholders = true,

            // 预刷新的距离，距离最后一个 item 多远时加载数据
            // 默认为 pageSize
            prefetchDistance = 4,

            /**
             * 初始化加载数量，默认为 pageSize * 3
             *
             * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
             * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
             */

            /**
             * 初始化加载数量，默认为 pageSize * 3
             *
             * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
             * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
             */

            /**
             * 初始化加载数量，默认为 pageSize * 3
             *
             * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
             * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
             */

            /**
             * 初始化加载数量，默认为 pageSize * 3
             *
             * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
             * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
             */
            initialLoadSize = ServiceLocator.NETWORK_PAGE_SIZE
        )
        return pagingConfig
    }


}