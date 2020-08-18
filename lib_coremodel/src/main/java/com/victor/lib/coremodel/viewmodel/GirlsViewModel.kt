package com.victor.lib.coremodel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.http.repository.IRepository

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsViewModel
 * Author: Victor
 * Date: 2020/7/8 下午 05:20
 * Description: 
 * -----------------------------------------------------------------
 */
class GirlsViewModel(val repository: IRepository) : ViewModel() {

    val datas = repository.postsOfSubreddit("", 20)

    /**
     * Factory for [LiveDataViewModel].
     */
    /*object GirlsLiveDataVMFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return GirlsViewModel(ServiceLocator.instance().getRepository(RepositoryType.GANK_GIRL)) as T
        }
    }*/
}
