package com.victor.lib.coremodel.http.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IRepository
 * Author: Victor
 * Date: 2020/7/8 下午 05:13
 * Description: 
 * -----------------------------------------------------------------
 */
interface IRepository {
    fun postsOfSubreddit(type: String?,pageSize: Int): Flow<PagingData<Any>>
}