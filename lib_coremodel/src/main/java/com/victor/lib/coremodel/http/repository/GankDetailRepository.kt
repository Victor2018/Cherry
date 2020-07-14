package com.victor.lib.coremodel.http.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.datasource.GankDetailPagingSource
import com.victor.lib.coremodel.http.datasource.GankGirlPagingSource

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlRepository
 * Author: Victor
 * Date: 2020/7/8 下午 05:12
 * Description: 
 * -----------------------------------------------------------------
 */
class GankDetailRepository (private val type: String,private val requestApi: ApiService) : IRepository {
    override fun postsOfSubreddit(pageSize: Int) = Pager(
        PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        )
    ) {
        GankDetailPagingSource(
            type = type,
            requestApi = requestApi
        )
    }.flow
}