package com.victor.lib.common.util

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import com.victor.lib.common.app.App
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BitmapUtil
 * Author: Victor
 * Date: 2020/7/15 下午 05:48
 * Description: 
 * -----------------------------------------------------------------
 */
class BitmapUtil {
    companion object {
        fun drawableToBitmap(drawable: Drawable): Bitmap? {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
            val canvas = Canvas(bitmap)

            //canvas.setBitmap(bitmap);
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }

        fun saveBitmap(
            bitmap: Bitmap,
            dir: String?,
            name: String,
            isShowPhotos: Boolean
        ): Boolean {
            val path = File(dir)
            if (!path.exists()) {
                path.mkdirs()
            }
            val file = File("$path/$name")
            if (file.exists()) {
                file.delete()
            }
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                }
            } else {
                return true
            }
            var fileOutputStream: FileOutputStream? = null
            try {
                fileOutputStream = FileOutputStream(file)
                bitmap.compress(
                    Bitmap.CompressFormat.PNG, 100,
                    fileOutputStream
                )
                fileOutputStream.flush()
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            } finally {
                try {
                    fileOutputStream!!.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // 其次把文件插入到系统图库
            if (isShowPhotos) {
                try {
                    MediaStore.Images.Media.insertImage(
                        App.get().getContentResolver(),
                        file.absolutePath, name, null
                    )
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                // 最后通知图库更新
                App.get().sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://$file")
                    )
                )
            }
            return true
        }

        fun getBlurBitMap(bm: Bitmap?, view: View): Bitmap? {
            val radius = 25
            val scaleFactor = 8f
            val overlay = Bitmap.createBitmap(
                (view.measuredWidth / scaleFactor).toInt(),
                (view.measuredHeight / scaleFactor).toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(overlay)
            canvas.translate(-view.left / scaleFactor, -view.top / scaleFactor)
            canvas.scale(1 / scaleFactor, 1 / scaleFactor)
            val paint = Paint()
            paint.flags = Paint.FILTER_BITMAP_FLAG
            canvas.drawBitmap(bm!!, 0f, 0f, paint)
            return overlay
        }
    }
}