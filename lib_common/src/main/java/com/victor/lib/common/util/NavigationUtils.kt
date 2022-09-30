package com.victor.lib.common.util

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.neuro.router.core.NeuroRouter
import com.victor.neuro.router.core.plugin.PluginConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NavigationUtils
 * Author: Victor
 * Date: 2020/7/4 下午 04:09
 * Description: ARouter路由跳转工具类
 * -----------------------------------------------------------------
 */
object NavigationUtils {
    val TITLE_KEY = "TITLE_KEY"
    val TYPE_KEY = "TYPE_KEY"
    val GANK_DATA_KEY = "GANK_DATA_KEY"
    val POSITION_KEY = "POSITION_KEY"
    val ID_KEY = "ID_KEY"

    /**
     * 去往首页
     */
    fun goHomeActivity() {
        ARouter.getInstance().build(ARouterPath.HomeAct).navigation()
    }
    /**
     * 去往个人页面
     */
    fun goMineActivity() {
        ARouter.getInstance().build(ARouterPath.MineAct).navigation()
    }
    /**
     * 去往干货详情页面
     */
    fun goGankActivity(title: String,type: String) {
        ARouter.getInstance().build(ARouterPath.GankAct)
            .withString(TITLE_KEY,title)
            .withString(TYPE_KEY,type)
            .navigation()
    }
    /**
     * 去往干货详情页面
     */
    fun goArticleActivity(title: String,id: Int) {
        ARouter.getInstance().build(ARouterPath.ArticleAct)
            .withString(TITLE_KEY,title)
            .withInt(ID_KEY,id)
            .navigation()
    }
    /**
     * 去往搜索干货页面
     */
    fun goSearchGankActivity(context: Context) {
//        ARouter.getInstance().build(ARouterPath.SearchGankAct).navigation()
        NeuroRouter.instance.navigation(PluginConfig.ROUTE_BASE_URI + ARouterPath.SearchGankAct,context)
    }

    /**
     * 去往妹子详情页面
     */
    fun goGirlsDetailActivity(position: Int,datas: ArrayList<GankDetailInfo>) {
        ARouter.getInstance().build(ARouterPath.GirlsDetailAct)
            .withInt(POSITION_KEY,position)
            .withSerializable(GANK_DATA_KEY,datas)
            .navigation()
    }
    /**
     * 去往妹子详情页面
     */
    fun goGirlsDetailActivity() {
        ARouter.getInstance().build(ARouterPath.GirlsDetailAct)
            .navigation()
    }
}