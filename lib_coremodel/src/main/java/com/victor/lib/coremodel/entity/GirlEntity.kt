package com.victor.lib.coremodel.entity

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlEntity
 * Author: Victor
 * Date: 2020/7/6 下午 07:29
 * Description: 
 * -----------------------------------------------------------------
 */
data class GirlEntity (
    val status: Int = 0,
    val page: Int = 0,
    val page_count: Int = 0,
    val total_counts: Int = 0,
    val data: List<GirlInfo>? = null
)