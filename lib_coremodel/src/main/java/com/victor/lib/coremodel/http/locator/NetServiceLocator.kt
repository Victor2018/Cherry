package com.victor.lib.coremodel.http.locator

import androidx.paging.PagingConfig
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.repository.GankDetailRepository
import com.victor.lib.coremodel.http.repository.GankGirlRepository
import com.victor.lib.coremodel.http.repository.IRepository

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

    override fun getRepository(type: RepositoryType): IRepository {
        when (type) {
            RepositoryType.GANK_GIRL -> {
                return GankGirlRepository(requestApi = getRequestApi(),pageConfig = pagingConfig)
            }
            RepositoryType.GANK_DETAIL -> {
                return GankDetailRepository(requestApi = getRequestApi(),pageConfig = pagingConfig)
            }
        }
        return GankGirlRepository(requestApi = getRequestApi(),pageConfig = pagingConfig)
    }
    override fun getRequestApi(): ApiService = api

    val pagingConfig = PagingConfig(
        // 每页显示的数据的大小
        pageSize = NETWORK_PAGE_SIZE,

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
        initialLoadSize = NETWORK_PAGE_SIZE
    )

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

}