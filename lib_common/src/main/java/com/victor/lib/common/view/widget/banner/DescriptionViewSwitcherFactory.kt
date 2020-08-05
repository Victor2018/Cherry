package com.victor.lib.common.view.widget.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ViewSwitcher
import com.victor.lib.common.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DescriptionViewSwitcherFactory
 * Author: Victor
 * Date: 2020/8/5 下午 03:31
 * Description: 
 * -----------------------------------------------------------------
 */
class DescriptionViewSwitcherFactory(var context: Context): ViewSwitcher.ViewFactory {
    override fun makeView(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.home_description_item, null)
    }
}