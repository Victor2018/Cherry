package com.victor.lib.coremodel.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsRemoteKey
 * Author: Victor
 * Date: 2020/8/19 上午 10:35
 * Description: 
 * -----------------------------------------------------------------
 */

@Entity(tableName = "grils_remote_keys")
data class GirlsRemoteKey (
    @PrimaryKey
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val subreddit: String, // technically mutable but fine for a demo
    val nextPageKey: String?
)