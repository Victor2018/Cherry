package com.victor.module.home.view.widget

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ViewSwitcher
import com.victor.module.home.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BannerImageSwitcherFactory
 * Author: Victor
 * Date: 2020/7/27 上午 09:57
 * Description: 
 * -----------------------------------------------------------------
 */
class BannerImageSwitcherFactory(var context: Context): ViewSwitcher.ViewFactory {
    override fun makeView(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.home_banner_item, null)
    }


}