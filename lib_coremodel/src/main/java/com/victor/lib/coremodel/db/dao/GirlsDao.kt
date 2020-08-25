package com.victor.lib.coremodel.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.victor.lib.coremodel.data.ChannelInfo
import com.victor.lib.coremodel.data.GankDetailInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsDao
 * Author: Victor
 * Date: 2020/8/18 上午 11:16
 * Description: 
 * -----------------------------------------------------------------
 */

@Dao
interface GirlsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<GankDetailInfo>)

    @Delete
    fun delete(data: GankDetailInfo)

    @Query("DELETE FROM girls")
    suspend fun clearAll()

    @Update
    suspend fun update(data: GankDetailInfo)

    @Query("SELECT * FROM girls ORDER BY publishedAt DESC")
    fun getAll(): LiveData<List<GankDetailInfo>>

    @Query("SELECT * FROM girls WHERE isFavorited = 1 ORDER BY publishedAt ASC LIMIT 5")
    fun getFavGirls(): LiveData<List<GankDetailInfo>>

    @Query("SELECT count(1) FROM girls WHERE isFavorited = 1")
    fun getFavGirlsCount(): LiveData<Int>

    @Query("SELECT EXISTS(SELECT 1 FROM girls WHERE _id = :id LIMIT 1)")
    fun isFavorited(id: String): LiveData<Boolean>

}