package com.victor.cherry

import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.MainHandler
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.http.datasource.RandomGirlDataSource
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.viewmodel.GirlsViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import org.victor.funny.util.ResUtils

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

    private val viewmodel: GirlsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GirlsViewModel(
                    ServiceLocator.instance().getRepository(RepositoryType.GANK_GIRL,this@SplashActivity),
                    RandomGirlDataSource(Dispatchers.IO)
                ) as T
            }
        }
    }

    override fun getLayoutResource() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        subscribeUi()
    }


    fun initData () {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val showGirl = sharedPref.getBoolean(ResUtils.getStringRes(R.string.girl), false)
        if (showGirl) {
            viewmodel.fetchRandomGirlData()
            return
        }
        toMain()
    }

    private fun subscribeUi() {
        viewmodel.randomGirlDataValue.observe(this, Observer {
            it.let {
                ImageUtils.instance.loadImage(this,mIvGirl,it.data?.get(0)?.images?.get(0))

                if (!TextUtils.isEmpty(it.data?.get(0)?.images?.get(0))) {
                    MainHandler.get().sendEmptyMessageDelayed(Constant.Action.GO_MAIN,3000)
                }
            }
        })
    }

    fun toMain () {
        MainActivity.intentStart(this)
        finish()
    }

    override fun handleMainMessage(message: Message?) {
        super.handleMainMessage(message)
        when (message?.what) {
            Constant.Action.GO_MAIN -> {
                toMain()
            }
        }
    }
}
