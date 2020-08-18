package com.victor.lib.coremodel.http.repository

import com.victor.lib.coremodel.db.dao.CategoryDao
import com.victor.lib.coremodel.db.dao.GirlsDao

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

class LocalGirlsRepository private constructor(private val girlsDao: GirlsDao) {

    fun getLocalGirls() = girlsDao.getAll()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: LocalGirlsRepository? = null

        fun getInstance(girlsDao: GirlsDao) =
            instance ?: synchronized(this) {
                instance ?: LocalGirlsRepository(girlsDao).also { instance = it }
            }
    }
}