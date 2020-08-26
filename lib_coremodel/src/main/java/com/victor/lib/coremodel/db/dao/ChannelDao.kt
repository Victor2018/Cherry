package com.victor.lib.coremodel.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.ChannelInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ChannelDao
 * Author: Victor
 * Date: 2020/8/12 下午 06:25
 * Description: 
 * -----------------------------------------------------------------
 */

@Dao
interface ChannelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<ChannelInfo>)

    @Delete
    fun delete(data: ChannelInfo)

    @Query("DELETE FROM channelinfo")
    suspend fun clearAll()

    @Update
    fun update(data: ChannelInfo)

    @Query("SELECT * FROM channelinfo")
    fun getAll(): LiveData<List<ChannelInfo>>

    @Query("SELECT * FROM channelinfo WHERE category_id = :categoryId")
    fun loadAllById(categoryId: Int): LiveData<List<ChannelInfo>>
}