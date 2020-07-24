package com.victor.lib.common.util

import android.content.res.AssetManager
import com.victor.lib.common.app.App
import java.io.ByteArrayOutputStream
import java.io.IOException

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AssetsJsonReaderUtil
 * Author: Victor
 * Date: 2020/7/24 上午 11:22
 * Description: 
 * -----------------------------------------------------------------
 */
object AssetsJsonReaderUtil {
    fun getJsonString(fileName: String?): String? {
        val baos = ByteArrayOutputStream()
        try {
            val assetManager: AssetManager = App.get().getAssets()
            val `is` = assetManager.open(fileName!!)
            val buffer = ByteArray(1024)
            var size = `is`.read(buffer)
            while (size != -1) {
                baos.write(buffer, 0, size)
                size = `is`.read(buffer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Loger.e("AssetsJsonReaderUtil","baos.toString() = " + baos.toString())
        return baos.toString()
    }
}