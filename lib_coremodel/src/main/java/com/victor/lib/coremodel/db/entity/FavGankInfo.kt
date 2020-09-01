package com.victor.lib.coremodel.db.entity

import androidx.room.*
import com.victor.lib.coremodel.data.GankDetailInfo
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FavGankInfo
 * Author: Victor
 * Date: 2020/9/1 上午 11:07
 * Description: 
 * -----------------------------------------------------------------
 */

@Entity(
    tableName = "fav_gank",
    foreignKeys = [
        ForeignKey(entity = GankDetailInfo::class, parentColumns = ["_id"], childColumns = ["fav_id"])
    ],
    indices = [Index("fav_id")]
)
data class FavGankInfo(
    @ColumnInfo(name = "fav_id") val favId: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "category") val category: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var favGankId: Long = 0
}