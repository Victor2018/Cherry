package com.victor.lib.coremodel.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArticleData
 * Author: Victor
 * Date: 2020/7/23 上午 11:14
 * Description: 
 * -----------------------------------------------------------------
 */
class ArticleData {
    var curPage: Int = 0
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var datas: List<ArticleInfo>? = null

}