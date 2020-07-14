package com.victor.lib.common.base

import android.app.Application
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseApplication
 * Author: Victor
 * Date: 2020/7/3 下午 06:28
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseApplication: Application() {
    companion object {
        private var instance : BaseApplication ?= null
        public fun get() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (AppConfig.MODEL_DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
        MultiDex.install(this)
    }
}