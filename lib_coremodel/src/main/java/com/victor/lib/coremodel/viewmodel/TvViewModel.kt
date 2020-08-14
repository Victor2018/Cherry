package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.victor.lib.coremodel.db.entity.CategoryInfo
import com.victor.lib.coremodel.http.repository.TvRepository

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvViewModel
 * Author: Victor
 * Date: 2020/8/14 下午 05:59
 * Description: 
 * -----------------------------------------------------------------
 */

class TvViewModel internal constructor(plantRepository: TvRepository) : ViewModel() {

    val categories: LiveData<List<CategoryInfo>> = plantRepository.getCategories()

}
