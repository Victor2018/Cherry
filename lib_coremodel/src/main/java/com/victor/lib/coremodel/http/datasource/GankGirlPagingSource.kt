package com.victor.lib.coremodel.http.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import com.victor.lib.coremodel.http.ApiService

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.alibaba.fastjson.JSON
import com.victor.lib.coremodel.db.AppDatabase
import retrofit2.HttpException
import java.io.IOException

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlPagingSource
 * Author: Victor
 * Date: 2020/7/8 下午 05:04
 * Description:  A [PagingSource] that uses the "name" field of posts as the key for next/prev pages.
 * Note that this is not the correct consumption of the Reddit API but rather shown here as an
 * alternative implementation which might be more suitable for your backend.
 * -----------------------------------------------------------------
 */
class GankGirlPagingSource (
    private val requestApi: ApiService,
    private val db: AppDatabase
) : PagingSource<Int, Any>() {
    val TAG = "GankGirlPagingSource"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        return try {
            Log.e(TAG,"load-params = " + JSON.toJSONString(params))

            Log.e(TAG,"load-params.key = " + params.key)
            var page = params.key ?: 0

            if (params is LoadParams.Refresh) {
                page = 0
                Log.e(TAG,"load-params is Refresh")
            } else if (params is LoadParams.Prepend) {
                Log.e(TAG,"load-params is Prependy")
            } else if (params is LoadParams.Append) {
                Log.e(TAG,"load-params is Append")
            }

            val items = requestApi.getFuliList(page = page,count = params.loadSize)

            db.withTransaction {
                if (items.page == 1) {
//                    db.girlsDao().clearAll()
                }
                db.girlsDao().insertAll(items.data!!)
            }


            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (items.page == items.page_count) null else page + 1

            LoadResult.Page(
                data = items.data!!,
                prevKey = null,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        /**
         * The name field is a unique identifier for post items.
         * (no it is not the title of the post :) )
         * https://www.reddit.com/dev/api
         */
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.hashCode()
        }
    }
}