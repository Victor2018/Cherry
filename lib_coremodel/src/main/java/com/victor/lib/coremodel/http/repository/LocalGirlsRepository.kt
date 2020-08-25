package com.victor.lib.coremodel.http.repository

import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.db.AppDatabase

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LocalGirlsRepository
 * Author: Victor
 * Date: 2020/8/14 下午 05:55
 * Description: 
 * -----------------------------------------------------------------
 */

class LocalGirlsRepository private constructor(private val db: AppDatabase) {
    val girlsDao = db.girlsDao()

    fun getLocalGirls() = girlsDao.getAll()

    fun getFavGirls() = girlsDao.getFavGirls()
    fun getFavGirlsCount() = girlsDao.getFavGirlsCount()
    fun isGirlFavorited(id: String) = girlsDao.isFavorited(id)
    suspend fun updateGirl(data: GankDetailInfo) = girlsDao.update(data)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: LocalGirlsRepository? = null

        fun getInstance(db: AppDatabase) =
            instance ?: synchronized(this) {
                instance ?: LocalGirlsRepository(db).also { instance = it }
            }
    }
}