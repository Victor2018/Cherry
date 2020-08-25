package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.http.repository.LocalGirlsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

class LocalGirlsViewModel internal constructor(var repository: LocalGirlsRepository) : ViewModel() {

    val girls = repository.getLocalGirls()
    val favGirls = repository.getFavGirls()
    val favGirlsCount = repository.getFavGirlsCount()

    fun updateGirl(data: GankDetailInfo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateGirl(data)
            }
        }
    }
}
