package com.victor.module.girls.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.victor.lib.common.util.BitmapUtil
import com.victor.lib.common.view.widget.PinchImageView
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.girls.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlDetailAdapter
 * Author: Victor
 * Date: 2020/7/15 下午 05:15
 * Description: 
 * -----------------------------------------------------------------
 */
class GirlDetailAdapter(var context: Context,var datas: MutableList<GankDetailInfo>): PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private var mCurrentView: View? = null
    private val mOnClickListener: View.OnClickListener? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        mCurrentView = `object` as View
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val imageUrl: String = datas[position].images?.get(0)!!
        val view: View =
            layoutInflater?.inflate(R.layout.gank_detail_item, container, false)!!
        val imageView: PinchImageView =
            view.findViewById<View>(R.id.iv_img) as PinchImageView
        imageView.setOnClickListener(mOnClickListener)
        Glide.with(context)
            .load(imageUrl)
            .thumbnail(0.2f)
            .into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        container.removeView(`object` as View)
    }

    fun getCurrentView(): Bitmap? {
        val pinchImageView: PinchImageView =
            mCurrentView!!.findViewById<View>(R.id.iv_img) as PinchImageView
        val drawable: Drawable = pinchImageView.getDrawable()
        return BitmapUtil.drawableToBitmap(drawable)
    }
}