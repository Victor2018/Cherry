package com.victor.module.home.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.app.App
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.lib.coremodel.entity.GankInfo
import com.victor.module.home.R
import kotlinx.android.synthetic.main.rv_gank_cell.view.*
import kotlinx.android.synthetic.main.rv_home_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankAdapter
 * Author: Victor
 * Date: 2020/7/13 下午 02:23
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchGankAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<GankDetailInfo, RecyclerView.ViewHolder>(context, listener)  {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: GankDetailInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_gank_cell ,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: GankDetailInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        contentViewHolder.itemView.tv_title.setText(
            Html.fromHtml(
                ("<a href=\""
                        + data?.url) + "\">"
                        + data?.desc + "</a>" + "[" + data?.author
                    .toString() + "]"
            )
        )

        if (data.images?.size!! > 0) {
            contentViewHolder.itemView.iv_image.visibility = View.VISIBLE
            ImageUtils.instance.loadImage(App.get(),contentViewHolder.itemView.iv_image,data.images?.get(0))
        } else {
            contentViewHolder.itemView.iv_image.visibility = View.GONE
        }

        contentViewHolder.itemView.tv_time.text = data?.publishedAt

        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}