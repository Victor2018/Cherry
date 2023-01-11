package com.victor.lib.common.util

import android.content.Context
import com.victor.lib.common.base.ARouterPath
import com.victor.neuro.router.core.NeuroRouter
import org.json.JSONObject

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
     * 去往干货详情页面
     */
    fun goArticleActivity(context: Context,title: String,id: Int) {
        var json = JSONObject()
        json.put(TITLE_KEY,title)
        json.put(ID_KEY,id)

        var data = json.toString()

        NeuroRouter.instance.navigation(ARouterPath.ArticleAct,data,context)
    }
    /**
     * 去往搜索干货页面
     */
    fun goSearchGankActivity(context: Context) {
        NeuroRouter.instance.navigation(ARouterPath.SearchGankAct,context)
    }

}