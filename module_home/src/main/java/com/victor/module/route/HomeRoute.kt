package com.victor.module.route

import com.victor.lib.common.base.ARouterPath
import com.victor.module.home.view.HomeFragment
import com.victor.neuro.router.core.NeuroRouter
import com.victor.neuro.router.core.plugin.IRoute

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeRoute
 * Author: Victor
 * Date: 2022/9/30 11:32
 * Description: 
 * -----------------------------------------------------------------
 */

class HomeRoute: IRoute {
    override fun register() {
//        NeuroRouter.instance.registerRoute(ARouterPath.HomeFgt, HomeFragment::class.java)
        NeuroRouter.instance.registerRoute("home/module/HomeFragment", HomeFragment::class.java)
    }
}