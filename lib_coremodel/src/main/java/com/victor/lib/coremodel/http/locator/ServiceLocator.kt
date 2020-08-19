package com.victor.lib.coremodel.http.locator

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.paging.PagingConfig
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.repository.IRepository

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ServiceLocator
 * Author: Victor
 * Date: 2020/7/8 下午 05:14
 * Description: Super simplified service locator implementation to allow us to
 * replace default implementationsfor testing.
 * -----------------------------------------------------------------
 */
interface ServiceLocator {
    companion object {
        const val NETWORK_PAGE_SIZE = 30

        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = NetServiceLocator()
                }
                return instance!!
            }
        }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }

    }

    fun getRepository(type: RepositoryType,context: Context): IRepository

    fun getRequestApi(type: RepositoryType): ApiService

    fun getPagingConfigs() :PagingConfig


}
