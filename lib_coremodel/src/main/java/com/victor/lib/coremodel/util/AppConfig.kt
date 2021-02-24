package com.victor.lib.coremodel.util

import com.victor.lib.coremodel.BuildConfig


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppConfig
 * Author: Victor
 * Date: 2021/2/24 14:22
 * Description: 
 * -----------------------------------------------------------------
 */
object AppConfig {
    /**
     * 开发者模式
     */
    const val MODEL_DEBUG = BuildConfig.MODEL_DEBUG
    /**
     * 线上模式
     */
    const val MODEL_ONLINE = BuildConfig.MODEL_ONLINE

    /**
     * 编译版本
     */
    const val BUILD_CODE = BuildConfig.BUILD_CODE


    /**
     * QQ KEY
     */
    const val QQ_APP_ID = "1110405869"

    /**
     * 微信AppID
     */
    const val WECHAT_APP_ID = "wx92207d67d5931463"

    const val UMENG_APP_KEY = "5eb2ade5167edd828d0000a4"

    const val UMENG_MESSAGE_SECRET = "b1eb2372a6aab2ce90ec65b7cdfaf110"
    const val SCHEMA = "flash://"

    const val PACKAGE_QQ = "com.tencent.mobileqq"
}