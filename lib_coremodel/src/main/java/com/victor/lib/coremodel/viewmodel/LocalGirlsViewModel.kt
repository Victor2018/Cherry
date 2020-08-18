package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.db.entity.CategoryInfo
import com.victor.lib.coremodel.http.repository.GankGirlRepository
import com.victor.lib.coremodel.http.repository.LocalGirlsRepository
import com.victor.lib.coremodel.http.repository.TvRepository

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LocalGirlsViewModel
 * Author: Victor
 * Date: 2020/8/14 下午 05:59
 * Description: 
 * -----------------------------------------------------------------
 */

class LocalGirlsViewModel internal constructor(repository: LocalGirlsRepository) : ViewModel() {

    val girls: LiveData<List<GankDetailInfo>> = repository.getLocalGirls()
}
