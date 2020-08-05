package com.victor.module.home.view.adapter

import android.R
import android.content.Context
import androidx.core.content.ContextCompat
import com.victor.lib.common.util.ColorUtil
import com.victor.lib.coremodel.entity.HotKeyInfo
import com.yalantis.filter.adapter.FilterAdapter
import com.yalantis.filter.widget.FilterItem

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchFilterAdapter
 * Author: Victor
 * Date: 2020/7/30 下午 03:53
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchFilterAdapter(var context: Context,
                          items: List<HotKeyInfo>?,
                          var mColors: IntArray?): FilterAdapter<HotKeyInfo>(items!!) {

    override fun createView(position: Int, item: HotKeyInfo): FilterItem {
        val filterItem = FilterItem(context)
        filterItem.strokeColor = mColors?.get(0)
        filterItem.textColor = mColors?.get(0)
        filterItem.cornerRadius = 50f
        filterItem.checkedTextColor =
            ContextCompat.getColor(context, R.color.white)
        filterItem.color = ContextCompat.getColor(context, R.color.white)
        filterItem.checkedColor = ColorUtil.getColorByHashCode(position.hashCode())
        filterItem.text = item.getText()
        filterItem.deselect()
        return filterItem
    }
}