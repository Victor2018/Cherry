package com.victor.lib.coremodel.dao

import androidx.room.*
import com.victor.lib.coremodel.entity.ChannelCategory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ChannelCategoryDao
 * Author: Victor
 * Date: 2020/8/12 下午 06:25
 * Description: 
 * -----------------------------------------------------------------
 */

@Dao
interface ChannelCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg datas: List<ChannelCategory>)

    @Delete
    fun delete(data: ChannelCategory)

    @Update
    fun update(data: ChannelCategory)

    @Query("SELECT * FROM channelcategory")
    fun getAll(): List<ChannelCategory>

    @Query("SELECT * FROM channelcategory WHERE channel_category IN (:category)")
    fun loadAllByIds(category: String): List<ChannelCategory>
}