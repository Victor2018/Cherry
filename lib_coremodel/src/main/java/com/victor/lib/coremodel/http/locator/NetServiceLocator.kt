package com.victor.lib.coremodel.http.locator

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
                return GankGirlRepository(requestApi = getRequestApi())
            }
            RepositoryType.GANK_DETAIL -> {
                return GankDetailRepository(requestApi = getRequestApi())
            }
        }
        return GankGirlRepository(requestApi = getRequestApi())
    }
    override fun getRequestApi(): ApiService = api

}