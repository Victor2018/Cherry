package com.victor.lib.coremodel.viewmodel.factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.http.datasource.WeChatDataSource
import com.victor.lib.coremodel.viewmodel.WeChatViewModel
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

class WechatViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return WeChatViewModel(
            WeChatDataSource(Dispatchers.IO)
        ) as T
    }
}