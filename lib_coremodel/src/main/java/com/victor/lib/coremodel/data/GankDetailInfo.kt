package com.victor.lib.coremodel.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.victor.lib.coremodel.db.GankImageUrlConverters
import java.io.Serializable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankDetailInfo
 * Author: Victor
 * Date: 2020/7/6 下午 07:31
 * Description: 
 * -----------------------------------------------------------------
 */
@Entity(tableName = "gank")
@TypeConverters(GankImageUrlConverters::class)
data class GankDetailInfo (
    @PrimaryKey @ColumnInfo(name = "_id") val _id: String = "",
    @ColumnInfo(name = "author") val author: String = "",
    @ColumnInfo(name = "category") val category: String = "",
    @ColumnInfo(name = "createdAt") val createdAt: String = "",
    @ColumnInfo(name = "publishedAt") val publishedAt: String = "",
    @ColumnInfo(name = "desc") val desc: String = "",
    @ColumnInfo(name = "likeCounts") val likeCounts:Int = 0,
    @ColumnInfo(name = "stars") val stars:Int = 0,
    @ColumnInfo(name = "views") val views:Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "type") val type: String = "",
    @ColumnInfo(name = "url") val url: String = "",
    @ColumnInfo(name = "isFavorited") var isFavorited: Int = 0,

    @ColumnInfo(name = "images") val images: List<String>? = null
): Serializable