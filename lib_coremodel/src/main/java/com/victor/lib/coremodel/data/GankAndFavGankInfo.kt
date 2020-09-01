package com.victor.lib.coremodel.data

import androidx.room.Embedded
import androidx.room.Relation
import com.victor.lib.coremodel.db.entity.FavGankInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankAndFavGankInfo
 * Author: Victor
 * Date: 2020/9/1 上午 11:17
 * Description: 
 * -----------------------------------------------------------------
 */

data class GankAndFavGankInfo(
    @Embedded
    val plant: GankDetailInfo,

    @Relation(parentColumn = "_id", entityColumn = "fav_id")
    val gardenPlantings: List<FavGankInfo> = emptyList()
)