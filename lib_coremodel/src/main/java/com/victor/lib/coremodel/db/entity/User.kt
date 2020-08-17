package com.victor.lib.coremodel.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: User
 * Author: Victor
 * Date: 2020/8/14 下午 06:48
 * Description: 
 * -----------------------------------------------------------------
 */

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String = "423099",
    @ColumnInfo(name = "last_name") val lastName: String = "victor"
)