package com.victor.lib.coremodel.viewmodel.factory

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.http.datasource.GankCategoryDataSource
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.http.repository.IRepository
import com.victor.lib.coremodel.viewmodel.ArticleViewModel
import com.victor.lib.coremodel.viewmodel.GankCategoryViewModel
import com.victor.lib.coremodel.viewmodel.GankViewModel
import kotlinx.coroutines.Dispatchers


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankVMFactory
 * Author: Victor
 * Date: 2021/2/24 14:37
 * Description: 
 * -----------------------------------------------------------------
 */
class GankVMFactory(
    val type: String?, val context: Context,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        var repository = ServiceLocator.instance()
            .getRepository(RepositoryType.GANK_DETAIL,context)
        return GankViewModel(type,repository) as T
    }
}