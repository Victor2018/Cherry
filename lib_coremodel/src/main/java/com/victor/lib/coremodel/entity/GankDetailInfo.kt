package com.victor.lib.coremodel.entity

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
class GankDetailInfo: Serializable {
    val _id: String? = null
    val author: String? = null
    val category: String? = null
    val createdAt: String? = null
    val publishedAt: String? = null
    val desc: String? = null
    val likeCounts = 0
    val stars = 0
    val views = 0
    val title: String? = null
    val type: String? = null
    val url: String? = null

    val images: List<String>? = null
}