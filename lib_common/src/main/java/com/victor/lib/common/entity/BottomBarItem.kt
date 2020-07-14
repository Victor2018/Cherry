package com.victor.lib.common.entity

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.victor.lib.common.view.widget.bottombar.ReadableBottomBar

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BottomBarItem
 * Author: Victor
 * Date: 2020/7/13 下午 03:01
 * Description: 
 * -----------------------------------------------------------------
 */
data class BottomBarItem(
    val index: Int,
    val text: String,
    val textSize: Float,
    @ColorInt val textColor: Int,
    @ColorInt val iconColor: Int,
    val drawable: Drawable,
    val type: ReadableBottomBar.ItemType
)