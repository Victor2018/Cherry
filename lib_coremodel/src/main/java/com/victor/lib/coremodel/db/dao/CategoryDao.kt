package com.victor.lib.coremodel.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.Category
import com.victor.lib.coremodel.db.entity.CategoryInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CategoryDao
 * Author: Victor
 * Date: 2020/8/14 下午 05:56
 * Description: 
 * -----------------------------------------------------------------
 */

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<Category>)

    @Query("DELETE FROM category")
    suspend fun clearAll()

    @Delete
    fun delete(data: Category)

    @Update
    fun update(data: Category)

    @Query("SELECT * FROM category")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE id = :id")
    fun loadAllByIds(id: Int): LiveData<List<Category>>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM category WHERE id IN (SELECT DISTINCT(category_id) FROM channelinfo)")
    fun getCategoriesAndChannels(): LiveData<List<CategoryInfo>>
}