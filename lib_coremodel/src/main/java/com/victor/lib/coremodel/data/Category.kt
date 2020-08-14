package com.victor.lib.coremodel.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ChannelCategory
 * Author: Victor
 * Date: 2020/8/5 上午 11:29
 * Description: 
 * -----------------------------------------------------------------
 */
@Entity
data class Category (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "channel_category") var channel_category: String? = null ,
    @ColumnInfo(name = "channels") var channels: List<ChannelInfo>? = null
)