package com.victor.lib.coremodel.viewmodel

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.http.datasource.HomeDataSource
import com.victor.lib.coremodel.http.datasource.RandomGirlDataSource
import com.victor.lib.coremodel.http.repository.*
import kotlinx.coroutines.Dispatchers

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankViewModelFactory
 * Author: Victor
 * Date: 2020/8/14 下午 05:55
 * Description: 
 * -----------------------------------------------------------------
 */

class GirlViewModelFactory(
    private val repository: IRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return GirlsViewModel(repository,RandomGirlDataSource(Dispatchers.IO)) as T
    }
}