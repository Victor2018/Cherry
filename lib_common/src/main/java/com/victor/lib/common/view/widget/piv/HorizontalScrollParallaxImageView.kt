package com.victor.lib.common.view.widget.piv

import android.content.Context
import android.util.AttributeSet
import com.victor.lib.common.view.widget.piv.transformer.HorizontalParallaxTransformer

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HorizontalScrollParallaxImageView
 * Author: Victor
 * Date: 2020/7/27 下午 02:42
 * Description: 
 * -----------------------------------------------------------------
 */
class HorizontalScrollParallaxImageView : ScrollTransformImageView {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attributeSet: AttributeSet) : super(ctx, attributeSet)

    init {
        super.viewTransformer = HorizontalParallaxTransformer()
    }
}