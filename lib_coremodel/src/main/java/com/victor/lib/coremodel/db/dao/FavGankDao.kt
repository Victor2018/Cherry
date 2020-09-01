package com.victor.lib.coremodel.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.GankAndFavGankInfo
import com.victor.lib.coremodel.db.entity.FavGankInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FavGankDao
 * Author: Victor
 * Date: 2020/9/1 上午 11:13
 * Description: 
 * -----------------------------------------------------------------
 */

@Dao
interface FavGankDao {
    @Query("SELECT * FROM fav_gank")
    fun getFavGanks(): LiveData<List<FavGankInfo>>

    @Query("SELECT EXISTS(SELECT 1 FROM fav_gank WHERE fav_id = :favId LIMIT 1)")
    fun isFavorited(favId: String): LiveData<Boolean>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM gank WHERE _id IN (SELECT DISTINCT(fav_id) FROM fav_gank WHERE type=:type)")
    fun getGankFavs(type: String): LiveData<List<GankAndFavGankInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavGank(favGankInfo: FavGankInfo): Long

    @Delete
    suspend fun deleteFavGank(favGankInfo: FavGankInfo)

    @Query("DELETE  FROM fav_gank where fav_id=:favId AND type=:type")
    suspend fun deleteFavGank(favId: String, type:String)
}