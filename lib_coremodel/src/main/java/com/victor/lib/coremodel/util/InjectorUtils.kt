package com.victor.lib.coremodel.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.http.repository.IRepository
import com.victor.lib.coremodel.http.repository.LocalGirlsRepository
import com.victor.lib.coremodel.http.repository.TvRepository
import com.victor.lib.coremodel.viewmodel.factory.*
import com.victor.lib.coremodel.vm.factory.HomeVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: InjectorUtils
 * Author: Victor
 * Date: 2020/8/13 上午 10:35
 * Description: 
 * -----------------------------------------------------------------
 */

object InjectorUtils {
    fun getLocalGirlsRepository(context: Context): LocalGirlsRepository {
        return LocalGirlsRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext))
    }
    fun provideLocalGirlsViewModelFactory(activity: AppCompatActivity): LocalGirlsViewModelFactory {
        return LocalGirlsViewModelFactory(
            getLocalGirlsRepository(activity),
            activity
        )
    }
    fun provideLocalGirlsViewModelFactory(fragment: Fragment): LocalGirlsViewModelFactory {
        return LocalGirlsViewModelFactory(
            getLocalGirlsRepository(fragment.requireContext()),
            fragment
        )
    }

    private fun getTvRepository(context: Context): TvRepository {
        return TvRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).channelCategoryDao())
    }

    fun provideTvViewModelFactory(fragment: Fragment): TvViewModelFactory {
        return TvViewModelFactory(
            getTvRepository(fragment.requireContext()),
            fragment
        )
    }


    fun getGirlRepository(context: Context): IRepository {
        return ServiceLocator.instance().getRepository(RepositoryType.GANK_GIRL,context!!)
    }

    fun provideWechatViewModelFactory(owner: SavedStateRegistryOwner): WechatViewModelFactory {
        return WechatViewModelFactory(
            owner
        )
    }

    fun provideArticleVMFactory( id: Int, context: Context,owner: SavedStateRegistryOwner): ArticleVMFactory {
        return ArticleVMFactory(id,context,owner)
    }

    fun provideHomeVMFactory(owner: SavedStateRegistryOwner): HomeVMFactory {
        return HomeVMFactory(owner)
    }

}