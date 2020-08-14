package com.victor.lib.coremodel.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ChannelInfo
 * Author: Victor
 * Date: 2020/8/5 上午 11:29
 * Description: 
 * -----------------------------------------------------------------
 */
@Entity
data class ChannelInfo (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "_id") var _id: Int = 0,//唯一标示
    @ColumnInfo(name = "category_id") var category_id: Int = 0,
    @ColumnInfo(name = "category") var category: Int = 0,
    @ColumnInfo(name = "gravity") var gravity: Int = 0,
    @ColumnInfo(name = "channel_name") var channel_name: String? = null,
    @ColumnInfo(name = "icon") var icon: String? = null,
    @ColumnInfo(name = "epg") var epg: String? = null,
    @ColumnInfo(name = "play_urls") var play_urls: List<ChannelPlayInfo>? = null,
    @ColumnInfo(name = "current") var current: Long = 0,
    @ColumnInfo(name = "duration") var duration: Long = 0
)