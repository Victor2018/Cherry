package com.victor.lib.common.util

import com.google.gson.Gson
import com.victor.lib.coremodel.util.Loger
import org.json.JSONException
import java.lang.reflect.Type


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: JsonUtils
 * Author: Victor
 * Date: 2020/7/24 上午 11:35
 * Description: 
 * -----------------------------------------------------------------
 */
object JsonUtils {
    private val mGson: Gson = Gson();
    /**
     * 日志标签
     */
    private const val TAG = "JsonUtils"

    fun toJSONString(`object`: Any): String? {
        try {
            return mGson.toJson(`object`)
        } catch (e: JSONException) {
            Loger.e(TAG, "JSONException: $`object`")
        }
        return null
    }

    fun <T> parseObject(text: String, clazz: Class<T>?): T? {
        try {
            return mGson.fromJson(text, clazz)
        } catch (e: JSONException) {
            Loger.e(TAG, "JSONException: $text")
        }
        return null
    }

    fun <T> parseObject(text: String, clazz: Type?): T? {
        try {
            return mGson.fromJson(text, clazz)
        } catch (e: JSONException) {
            Loger.e(TAG, "JSONException: $text")
        }
        return null
    }


}