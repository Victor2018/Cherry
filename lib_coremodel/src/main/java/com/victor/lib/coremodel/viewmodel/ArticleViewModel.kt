package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.ViewModel
import com.victor.lib.coremodel.http.repository.IRepository

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MineViewModel
 * Author: Victor
 * Date: 2020/7/8 下午 05:20
 * Description: 
 * -----------------------------------------------------------------
 */
class ArticleViewModel(val id: Int, val repository: IRepository) : ViewModel() {
    val datas = repository.postsOfSubreddit("",id)
}
