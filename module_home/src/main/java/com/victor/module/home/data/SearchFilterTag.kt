package com.victor.module.home.data

import androidx.annotation.NonNull
import com.yalantis.filter.model.FilterModel

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchFilterTag
 * Author: Victor
 * Date: 2020/7/30 下午 03:55
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchFilterTag: FilterModel {
    private var text: String? = null
    private var color = 0

    fun Tag(text: String?, color: Int) {
        this.text = text
        this.color = color
    }

    @NonNull
    override fun getText(): String {
        return text!!
    }

    fun setText(text: String?) {
        this.text = text
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
    }

    fun equals(o: Any): Boolean? {
        if (this === o) return true
        if (o !is SearchFilterTag) return false
        val tag: SearchFilterTag = o as SearchFilterTag
        return if (getColor() != tag.getColor()) false else getText() == tag.getText()
    }

    override fun hashCode(): Int {
        var result = getText().hashCode()
        result = 31 * result + getColor()
        return result
    }
}