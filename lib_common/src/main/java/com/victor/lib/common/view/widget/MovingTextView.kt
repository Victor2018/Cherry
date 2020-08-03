package com.victor.lib.common.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MovingTextView
 * Author: Victor
 * Date: 2020/7/31 下午 04:24
 * Description: 
 * -----------------------------------------------------------------
 */
@SuppressLint("AppCompatCustomView")
class MovingTextView: TextView {
    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun isFocused(): Boolean {
        return true
    }


}