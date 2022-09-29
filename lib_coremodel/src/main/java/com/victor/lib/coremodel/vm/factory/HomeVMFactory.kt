package com.victor.lib.coremodel.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.http.datasource.HomeDS
import com.victor.lib.coremodel.vm.HomeVM

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeVMFactory
 * Author: Victor
 * Date: 2022/9/29 12:19
 * Description: 
 * -----------------------------------------------------------------
 */

class HomeVMFactory(owner: SavedStateRegistryOwner) : BaseVMFactory(owner) {
    override fun getVM(): ViewModel {
        return HomeVM(HomeDS())
    }
}