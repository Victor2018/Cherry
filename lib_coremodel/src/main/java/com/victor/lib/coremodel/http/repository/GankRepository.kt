package com.victor.lib.coremodel.http.repository

import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.db.dao.CategoryDao
import com.victor.lib.coremodel.db.dao.FavGankDao
import com.victor.lib.coremodel.db.entity.FavGankInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankRepository
 * Author: Victor
 * Date: 2020/8/14 下午 05:55
 * Description: 
 * -----------------------------------------------------------------
 */

class GankRepository private constructor(private val db: AppDatabase) {
    val favGankDao = db.favGankDao()

    suspend fun addFavGank(favId: String,type:String,category: String) {
        val favGank = FavGankInfo(favId,type,category)
        favGankDao.insertFavGank(favGank)
    }
    suspend fun removeFavGank(favId: String, type:String) {
        favGankDao.deleteFavGank(favId,type)
    }
    suspend fun removeFavGank(favGank: FavGankInfo) {
        favGankDao.deleteFavGank(favGank)
    }

    fun isGankFavoreted(favId: String) = favGankDao.isFavorited(favId)

    fun getFavGanks(type: String) = favGankDao.getGankFavs(type)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: GankRepository? = null

        fun getInstance(db: AppDatabase) =
            instance ?: synchronized(this) {
                instance ?: GankRepository(db).also { instance = it }
            }
    }
}