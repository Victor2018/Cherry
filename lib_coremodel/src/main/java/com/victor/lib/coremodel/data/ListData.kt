package com.victor.lib.coremodel.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ListData
 * Author: Victor
 * Date: 2022/9/29 12:02
 * Description: 
 * -----------------------------------------------------------------
 */

open class ListData<T> {
    var curPage: Int = 0
    var offset: Int = 0
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var over: Boolean = false
    var datas: List<T>? = null
}