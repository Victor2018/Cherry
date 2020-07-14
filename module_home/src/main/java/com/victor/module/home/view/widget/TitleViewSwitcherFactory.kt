package com.victor.module.home.view.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ViewSwitcher
import com.victor.module.home.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TitleViewSwitcherFactory
 * Author: Victor
 * Date: 2020/7/14 上午 10:35
 * Description: 
 * -----------------------------------------------------------------
 */
class TitleViewSwitcherFactory(var context: Context): ViewSwitcher.ViewFactory {
    override fun makeView(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.home_title_item, null)
    }
}