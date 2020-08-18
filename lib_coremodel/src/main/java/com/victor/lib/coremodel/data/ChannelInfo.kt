package com.victor.lib.coremodel.data

import androidx.room.*
import com.victor.lib.coremodel.db.PlayUrlConverters

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

@Entity(tableName = "channelinfo",
    foreignKeys = [
        ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["category_id"])
    ],
    indices = [Index("category_id")])
@TypeConverters(PlayUrlConverters::class)
data class ChannelInfo (
    @ColumnInfo(name = "category_id") var category_id: Int = 0,
    @ColumnInfo(name = "category") var category: Int = 0,
    @ColumnInfo(name = "gravity") var gravity: Int = 0,
    @ColumnInfo(name = "channel_name") var channel_name: String? = null,
    @ColumnInfo(name = "icon") var icon: String? = null,
    @ColumnInfo(name = "epg") var epg: String? = null,
    @ColumnInfo(name = "play_urls") var play_urls: List<ChannelPlayInfo>? = null,
    @ColumnInfo(name = "current") var current: Long = 0,
    @ColumnInfo(name = "duration") var duration: Long = 0
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "channelId")
    var channelId: Int = 0
}