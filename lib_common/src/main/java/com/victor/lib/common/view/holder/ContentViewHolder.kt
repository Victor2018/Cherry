package com.victor.lib.common.view.holder

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:06
 * Description: 
 * -----------------------------------------------------------------
 */
open class ContentViewHolder: RecyclerView.ViewHolder,View.OnClickListener,View.OnLongClickListener {
    var mOnItemClickListener: AdapterView.OnItemClickListener? = null

    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener?) {
        mOnItemClickListener = listener
    }

    constructor(itemView: View) : super(itemView) {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    override fun onClick(view: View) {
        mOnItemClickListener?.onItemClick(null, view, getAdapterPosition(), 0)
    }

    override fun onLongClick(v: View): Boolean {
        mOnItemClickListener?.onItemClick(null, v, getAdapterPosition(), -1)
        return false
    }
}