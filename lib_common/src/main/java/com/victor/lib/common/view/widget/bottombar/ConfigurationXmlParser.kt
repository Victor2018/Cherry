package com.victor.lib.common.view.widget.bottombar

import android.content.Context
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.victor.lib.common.entity.BottomBarItemConfig
import org.xmlpull.v1.XmlPullParserException

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ConfigurationXmlParser
 * Author: Victor
 * Date: 2020/7/13 下午 03:06
 * Description: 
 * -----------------------------------------------------------------
 */
class ConfigurationXmlParser(private val context: Context, xmlRes: Int) {

    private val parser: XmlResourceParser = context.resources.getXml(xmlRes)
    private val itemConfigList: ArrayList<BottomBarItemConfig> = arrayListOf()

    fun parse(): ArrayList<BottomBarItemConfig> {
        itemConfigList.clear()
        try {
            var eventType: Int?
            do {
                eventType = parser.next()
                if (eventType == XmlResourceParser.START_TAG && KEY_TAB == parser.name) {
                    itemConfigList.add(getTabConfig(parser))
                }
            } while (eventType != XmlResourceParser.END_DOCUMENT)
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
            throw Exception()
        }
        return itemConfigList
    }

    private fun getTabConfig(parser: XmlResourceParser): BottomBarItemConfig {
        val attributeCount = parser.attributeCount
        var itemText: String? = null
        var itemDrawable: Drawable? = null
        for (i in 0 until attributeCount) {
            when (parser.getAttributeName(i)) {
                KEY_TEXT -> itemText = getText(parser, i)
                KEY_DRAWABLE -> itemDrawable = getDrawable(parser, i)
            }
        }
        return BottomBarItemConfig(text = itemText!!, drawable = itemDrawable!!, index = itemConfigList.size)
    }

    private fun getDrawable(parser: XmlResourceParser, i: Int): Drawable {
        return ContextCompat.getDrawable(context, parser.getAttributeResourceValue(i, 0))!!
    }

    private fun getText(parser: XmlResourceParser, i: Int): String {
        return context.getString(parser.getAttributeResourceValue(i, 0))
    }

    companion object {
        const val KEY_TEXT: String = "text"
        const val KEY_DRAWABLE: String = "drawable"

        const val KEY_TAB: String = "tab"
    }

}