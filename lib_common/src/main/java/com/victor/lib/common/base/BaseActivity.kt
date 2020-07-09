package com.victor.lib.common.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.R
import com.victor.lib.common.util.StatusBarUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseActivity
 * Author: Victor
 * Date: 2020/7/3 下午 06:17
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseActivity: AppCompatActivity() {
    protected var TAG = javaClass.simpleName

    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        initializeSuper()
    }

    fun initializeSuper () {
        StatusBarUtil.translucentStatusBar(this, true,false,false)
    }


    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
    }


    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_activity_enter_back, R.anim.anim_activity_exit_back);
    }
}