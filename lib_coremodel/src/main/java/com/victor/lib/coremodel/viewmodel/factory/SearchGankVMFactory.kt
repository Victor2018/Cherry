package com.victor.lib.coremodel.viewmodel.factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.http.datasource.GankCategoryDataSource
import com.victor.lib.coremodel.http.datasource.SearchGankDataSource
import com.victor.lib.coremodel.viewmodel.GankCategoryViewModel
import com.victor.lib.coremodel.viewmodel.SearchGankViewModel
import kotlinx.coroutines.Dispatchers


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankVMFactory
 * Author: Victor
 * Date: 2021/2/24 14:37
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchGankVMFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return SearchGankViewModel(SearchGankDataSource(Dispatchers.IO)) as T
    }
}