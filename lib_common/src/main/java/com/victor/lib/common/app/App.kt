package com.victor.lib.common.app

import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.bugly.crashreport.CrashReport
import com.victor.lib.common.base.AppConfig
import com.victor.lib.common.base.BaseApplication
import com.victor.lib.coremodel.util.WebConfig
import org.victor.http.lib.ApiClient

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: App
 * Author: Victor
 * Date: 2020/7/3 下午 06:54
 * Description: 
 * -----------------------------------------------------------------
 */
class App: BaseApplication() {
    companion object {
        private var instance : BaseApplication ?= null
        public fun get() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashReport.initCrashReport(this)
        ApiClient.BASE_URL = WebConfig.getBaseUrl()
    }

}