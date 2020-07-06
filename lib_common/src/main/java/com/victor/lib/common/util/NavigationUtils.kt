package com.victor.lib.common.util

import com.alibaba.android.arouter.launcher.ARouter
import com.victor.lib.common.base.ARouterPath

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
}