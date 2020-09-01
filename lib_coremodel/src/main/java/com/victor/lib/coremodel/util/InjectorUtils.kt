package com.victor.lib.coremodel.util

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.http.repository.GankRepository
import com.victor.lib.coremodel.http.repository.LocalGirlsRepository
import com.victor.lib.coremodel.http.repository.TvRepository
import com.victor.lib.coremodel.viewmodel.GankViewModelFactory
import com.victor.lib.coremodel.viewmodel.LocalGirlsViewModelFactory
import com.victor.lib.coremodel.viewmodel.TvViewModelFactory

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
    private fun getLocalGirlsRepository(context: Context): LocalGirlsRepository {
        return LocalGirlsRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext))
    }
    fun provideLocalGirlsViewModelFactory(activity: AppCompatActivity): LocalGirlsViewModelFactory {
        return LocalGirlsViewModelFactory(getLocalGirlsRepository(activity), activity)
    }
    fun provideLocalGirlsViewModelFactory(fragment: Fragment): LocalGirlsViewModelFactory {
        return LocalGirlsViewModelFactory(getLocalGirlsRepository(fragment.requireContext()), fragment)
    }

    private fun getTvRepository(context: Context): TvRepository {
        return TvRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).channelCategoryDao())
    }

    fun provideTvViewModelFactory(fragment: Fragment): TvViewModelFactory {
        return TvViewModelFactory(getTvRepository(fragment.requireContext()), fragment)
    }
    private fun getGankRepository(context: Context): GankRepository {
        return GankRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext))
    }

    fun provideGankViewModelFactory(fragment: Fragment): GankViewModelFactory {
        return GankViewModelFactory(getGankRepository(fragment.requireContext()), fragment)
    }

}