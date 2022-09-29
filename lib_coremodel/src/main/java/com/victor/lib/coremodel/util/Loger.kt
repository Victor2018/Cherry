package com.victor.lib.coremodel.util

import android.util.Log

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Loger
 * Author: Victor
 * Date: 2020/7/8 下午 05:58
 * Description: 日志打印工具
 * -----------------------------------------------------------------
 */
object Loger {
    fun d(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.d(TAG, msg.toString())
        }
    }

    fun e(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.e(TAG, msg.toString())
        }
    }

    fun i(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.i(TAG, msg.toString())
        }
    }
}