package com.victor.lib.coremodel.http.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import com.victor.lib.coremodel.http.ApiService

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankPagingSource
 * Author: Victor
 * Date: 2020/7/8 下午 05:04
 * Description:  A [PagingSource] that uses the "name" field of posts as the key for next/prev pages.
 * Note that this is not the correct consumption of the Reddit API but rather shown here as an
 * alternative implementation which might be more suitable for your backend.
 * -----------------------------------------------------------------
 */
class SearchGankPagingSource (
    private val type: String?,
    private val requestApi: ApiService
) : PagingSource<Int, Any>() {
    val TAG = "SearchGankPagingSource"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        return try {
            var page = params.key ?: 0
            if (params is LoadParams.Refresh) {
                page = 0
                Log.e(TAG,"load-params is Refresh")
            } else if (params is LoadParams.Prepend) {
                Log.e(TAG,"load-params is Prependy")
            } else if (params is LoadParams.Append) {
                Log.e(TAG,"load-params is Append")
            }
            val items = requestApi.searchGank(key = type,page = page,count = params.loadSize)

            LoadResult.Page(
                data = items.data!!,
                prevKey = if (items.page == 0) null else items.page - 1,
                nextKey = if (items.page == items.page_count) null else items.page + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
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