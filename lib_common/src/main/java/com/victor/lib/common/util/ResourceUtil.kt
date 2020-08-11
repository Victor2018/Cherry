package com.victor.lib.common.util

import android.content.Context

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ResourceUtil
 * Author: Victor
 * Date: 2020/8/11 上午 11:24
 * Description: 资源工具类,module中根据context动态获取application中对应资源文件
 * -----------------------------------------------------------------
 */

object ResourceUtil {
    /**
     * 获取布局资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getLayoutResId(context: Context, resName: String): Int {
        return getResId(context, "layout", resName)
    }

    /**
     * 获取字符串资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getStringResId(context: Context, resName: String): Int {
        return getResId(context, "string", resName)
    }

    /**
     * 获取Drawable资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getDrawableResId(context: Context, resName: String): Int {
        return getResId(context, "drawable", resName)
    }

    /**
     * 获取颜色资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getColorResId(context: Context, resName: String): Int {
        return getResId(context, "color", resName)
    }

    /**
     * 获取id文件中资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getIdRes(context: Context, resName: String): Int {
        return getResId(context, "id", resName)
    }

    /**
     * 获取数组资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getArrayResId(context: Context, resName: String): Int {
        return getResId(context, "array", resName)
    }

    /**
     * 获取style中资源
     *
     * @param context
     * @param resName
     * @return
     */
    fun getStyleResId(context: Context, resName: String): Int {
        return getResId(context, "style", resName)
    }

    /**
     * 获取context中对应类型的资源id
     *
     * @param context
     * @param type
     * @param resName
     * @return
     */
    private fun getResId(context: Context, type: String, resName: String): Int {
        return context.getResources().getIdentifier(resName, type, context.getPackageName())
    }
}