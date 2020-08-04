package com.victor.lib.common.util

import android.content.Context

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SharePreferencesUtil
 * Author: Victor
 * Date: 2020/8/4 上午 10:15
 * Description: 
 * -----------------------------------------------------------------
 */
object SharePreferencesUtil {
    private val TAG = "SharePreferencesUtil"

    fun putInt(context: Context, key: String, value: Int) {
        Loger.e(TAG, "putInt()-$key=$value")
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putInt(key, value)
        ed.commit()
    }

    fun getInt(context: Context, key: String): Int {
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val value = sp.getInt(key, 0)
        Loger.e(TAG, "putInt()-$key=$value")
        return value
    }
    fun getInt(context: Context, key: String,defaultValue: Int): Int {
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val value = sp.getInt(key, defaultValue)
        Loger.e(TAG, "putInt()-$key=$value")
        return value
    }

    fun putLong(context: Context, key: String, value: Long) {
        Loger.e(TAG, "putLong()-$key=$value")
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putLong(key, value)
        ed.commit()
    }

    fun getLong(context: Context, key: String): Long? {
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val value = sp.getLong(key, 0)
        Loger.e(TAG, "getLong()-$key=$value")
        return value
    }

    fun putString(context: Context, key: String, value: String) {
        Loger.e(TAG, "putString()-$key=$value")
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putString(key, value)
        ed.commit()
    }

    fun getString(context: Context, key: String, defaultValue: String): String? {
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val value = sp.getString(key, defaultValue)
        Loger.e(TAG, "getString()-$key=$value")
        return value
    }

    fun putBoolean(context: Context, key: String, value: Boolean) {
        Loger.e(TAG, "putBoolean()-$key=$value")
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putBoolean(key, value)
        ed.commit()
    }

    fun getBoolean(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val value = sp.getBoolean(key, false)
        Loger.e(TAG, "getBoolean()-$key=$value")
        return value
    }
    fun getBoolean(context: Context, key: String,defaultValue: Boolean): Boolean {
        val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
        val value = sp.getBoolean(key, defaultValue)
        Loger.e(TAG, "getBoolean()-$key=$value")
        return value
    }

}