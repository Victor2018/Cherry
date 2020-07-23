package com.victor.lib.common.base

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ARouterPath
 * Author: Victor
 * Date: 2020/7/3 下午 06:41
 * Description: 
 * -----------------------------------------------------------------
 */
object ARouterPath {
    private const val MAIN = "/main"
    private const val HOME = "/home"
    private const val WECHAT = "/wechat"
    private const val GIRLS = "/girls"
    private const val MINE = "/mine"

    /**主Activity */
    const val MainAct = "$MAIN/app/MainActivity"

    /**首页Activity */
    const val HomeAct = "$HOME/module/HomeActivity"

    /**首页Fragment */
    const val HomeFgt = "$HOME/module/HomeFragment"

    /**我的Activity */
    const val MineAct = "$MINE/module/MineActivity"

    /**我的Fragment */
    const val MineFgt = "$MINE/module/MineFragment"

    /**妹子Activity */
    const val GirlsAct = "$GIRLS/module/GirlsActivity"

    /**妹子Fragment */
    const val GirlsFgt = "$GIRLS/module/GirlsFragment"

    /**妹子详情Activity */
    const val GirlsDetailAct = "$GIRLS/module/GirlsDetailActivity"

    /**干货Activity */
    const val GankAct = "$HOME/module/GankActivity"

    /**公众号Fragment */
    const val WeChatFgt = "$WECHAT/module/WeChatFragment"

    /**公众号Activity */
    const val WeChatAct = "$WECHAT/module/WeChatActivity"

    /**公众号文章Activity */
    const val ArticleAct = "$WECHAT/module/ArticleActivity"

}