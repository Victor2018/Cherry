package com.victor.module.tv.view.holder

import android.view.View
import com.victor.lib.common.view.holder.ContentViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvCellContentViewHolder
 * Author: Victor
 * Date: 2020/7/24 下午 04:26
 * Description: 
 * -----------------------------------------------------------------
 */
class TvCellContentViewHolder(itemView: View, var parentPosition: Int) : ContentViewHolder(itemView) {

    override fun onClick(view: View) {
        mOnItemClickListener!!.onItemClick(null, view, getAdapterPosition(), parentPosition.toLong())
    }
}