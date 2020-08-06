package com.victor.lib.common.base

import com.victor.lib.common.BuildConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppConfig
 * Author: Victor
 * Date: 2020/7/4 下午 02:36
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
}