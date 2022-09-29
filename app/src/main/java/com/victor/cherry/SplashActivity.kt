package com.victor.cherry

import android.os.Bundle
import com.victor.lib.common.base.BaseActivity

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SplashActivity
 * Author: Victor
 * Date: 2020/8/25 上午 10:33
 * Description: 
 * -----------------------------------------------------------------
 */

class SplashActivity : BaseActivity() {

    override fun getLayoutResource() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    fun initData () {
        toMain()
    }

    fun toMain () {
        MainActivity.intentStart(this)
        finish()
    }

}
