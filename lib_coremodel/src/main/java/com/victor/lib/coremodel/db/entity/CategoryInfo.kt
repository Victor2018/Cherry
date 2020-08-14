package com.victor.lib.coremodel.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.victor.lib.coremodel.data.Category
import com.victor.lib.coremodel.data.ChannelInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CategoryInfo
 * Author: Victor
 * Date: 2020/8/14 下午 06:00
 * Description: 
 * -----------------------------------------------------------------
 */

data class CategoryInfo(
    @Embedded
    val category: Category,

    @Relation(parentColumn = "id", entityColumn = "category_id")
    val categoryChannels: List<ChannelInfo> = emptyList()
)