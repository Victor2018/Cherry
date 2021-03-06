package com.victor.lib.coremodel.http.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.http.ApiService
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
class GankGirlRepository (
    private val pageConfig: PagingConfig,
    private val requestApi: ApiService,
    private val db: AppDatabase) : IRepository {
    override fun postsOfSubreddit(type: String?,pageSize: Int) = Pager(
        config = pageConfig
    ) {
        GankGirlPagingSource(
            requestApi = requestApi,
            db = db
        )
    }.flow

}