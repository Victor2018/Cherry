package com.victor.lib.coremodel.entity

import com.yalantis.filter.model.FilterModel

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotKeyInfo
 * Author: Victor
 * Date: 2020/7/29 下午 03:53
 * Description: 
 * -----------------------------------------------------------------
 */
class HotKeyInfo: FilterModel {
    var id: Int = 0
    var order: Int = 0
    var visible: Int = 0
    var link: String? = null
    var name: String? = null
    override fun getText(): String {
        return name!!
    }

}