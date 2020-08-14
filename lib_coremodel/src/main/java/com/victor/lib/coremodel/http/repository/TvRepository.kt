package com.victor.lib.coremodel.http.repository

import com.victor.lib.coremodel.db.dao.CategoryDao

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvRepository
 * Author: Victor
 * Date: 2020/8/14 下午 05:55
 * Description: 
 * -----------------------------------------------------------------
 */

class TvRepository private constructor(private val categoryDao: CategoryDao) {

    fun getCategories() = categoryDao.getCategoriesAndChannels()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TvRepository? = null

        fun getInstance(plantDao: CategoryDao) =
            instance ?: synchronized(this) {
                instance ?: TvRepository(plantDao).also { instance = it }
            }
    }
}