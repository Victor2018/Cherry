package com.victor.lib.coremodel.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpUtil
 * Author: Victor
 * Date: 2020/9/2 下午 03:33
 * Description: 
 * -----------------------------------------------------------------
 */

object HttpUtil {
    @SuppressLint("MissingPermission")
    fun isNetEnable(context: Context): Boolean {
        val connManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkinfo = connManager.activeNetworkInfo
        return if (networkinfo == null || !networkinfo.isAvailable) {
            false
        } else true

    }
}