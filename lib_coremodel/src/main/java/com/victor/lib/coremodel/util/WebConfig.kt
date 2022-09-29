package com.victor.lib.coremodel.util

import android.net.Uri


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WebConfig
 * Author: Victor
 * Date: 2021/2/24 14:22
 * Description: 
 * -----------------------------------------------------------------
 */
object WebConfig {
    const val DEBUG_BASE_URL = "https://www.wanandroid.com/"
    const val ONLINE_BASE_URL = "https://www.wanandroid.com/"

    const val PAGE_SIZE = 19

    const val SEND_SMS = "auth/sendSms"

    fun getBaseUrl(): String {
        if (AppConfig.MODEL_DEBUG) {
            return DEBUG_BASE_URL
        }
        return ONLINE_BASE_URL
    }

    fun getServerIp(): String? {
        val uri: Uri = Uri.parse(getBaseUrl())
        return uri.getHost()
    }

    fun getRequestUrl(api: String): String? {
        return getBaseUrl().toString() + api
    }
}