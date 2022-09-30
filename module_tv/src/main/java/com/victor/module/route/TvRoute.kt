package com.victor.module.route

import com.victor.lib.common.base.ARouterPath
import com.victor.module.tv.view.TvFragment
import com.victor.neuro.router.core.NeuroRouter
import com.victor.neuro.router.core.plugin.IRoute

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvRoute
 * Author: Victor
 * Date: 2022/9/30 11:34
 * Description: 
 * -----------------------------------------------------------------
 */

class TvRoute : IRoute {
    override fun register() {
        NeuroRouter.instance.registerRoute(ARouterPath.TvFgt, TvFragment::class.java)
    }
}