package com.victor.lib.coremodel.entity

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
class ChannelInfo {
    var _id: Int = 0//唯一标示
    var category: Int = 0
    var gravity: Int = 0
    var channel_name: String? = null
    var icon: String? = null
    var epg: String? = null
    var play_urls: List<ChannelPlayInfo>? = null
    var current: Long = 0
    var duration: Long = 0
}