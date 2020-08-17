package com.victor.lib.coremodel.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.victor.lib.coremodel.data.ChannelPlayInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlayUrlConverters
 * Author: Victor
 * Date: 2020/8/17 下午 03:11
 * Description: 
 * -----------------------------------------------------------------
 */

class PlayUrlConverters {
    @TypeConverter
    fun stringToObject(value: String): List<ChannelPlayInfo> {
        val listType = object : TypeToken<List<ChannelPlayInfo>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Any>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}