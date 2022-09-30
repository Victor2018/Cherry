package com.victor.module.route

import com.victor.lib.common.base.ARouterPath
import com.victor.module.wechat.view.WeChatFragment
import com.victor.neuro.router.core.NeuroRouter
import com.victor.neuro.router.core.plugin.IRoute

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatRoute
 * Author: Victor
 * Date: 2022/9/30 11:35
 * Description: 
 * -----------------------------------------------------------------
 */

class WeChatRoute : IRoute {
    override fun register() {
        NeuroRouter.instance.registerRoute(ARouterPath.WeChatFgt, WeChatFragment::class.java)
    }
}