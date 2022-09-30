package com.victor.module.route

import com.victor.lib.common.base.ARouterPath
import com.victor.module.girls.view.GirlsFragment
import com.victor.neuro.router.core.NeuroRouter
import com.victor.neuro.router.core.plugin.IRoute

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsRoute
 * Author: Victor
 * Date: 2022/9/30 11:31
 * Description: 
 * -----------------------------------------------------------------
 */

class GirlsRoute : IRoute {
    override fun register() {
        NeuroRouter.instance.registerRoute(ARouterPath.GirlsFgt, GirlsFragment::class.java)
    }
}