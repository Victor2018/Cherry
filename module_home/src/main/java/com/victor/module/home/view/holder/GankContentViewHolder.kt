package com.victor.module.home.view.holder

import android.text.Html
import android.view.View
import com.victor.lib.common.app.App
import com.victor.lib.common.util.DateUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.data.HomeSquareInfo
import com.victor.module.home.R
import kotlinx.android.synthetic.main.rv_gank_cell.view.*
import org.victor.funny.util.ResUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankContentViewHolder
 * Author: Victor
 * Date: 2020/9/1 下午 03:00
 * Description: 
 * -----------------------------------------------------------------
 */

class GankContentViewHolder: ContentViewHolder {

    companion object {
        const val ONITEM_CLICK: Long = 0x801
        const val ONITEM_FAV_CLICK: Long = 0x802
    }

    constructor(itemView: View) : super(itemView) {
        itemView.mIvFavStatus.setOnClickListener(this)
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun bindData (data: HomeSquareInfo?, showThumbnail: Boolean) {

        itemView.mTvTitle.setText(
            Html.fromHtml(
                ("<a href=\""
                        + data?.link) + "\">"
                        + data?.title + "</a>" + "[" + data?.niceShareDate
                    .toString() + "]"
            )
        )

        itemView.mTvTime.text = data?.shareUser

        itemView.mIvFavStatus.setImageResource(if (data?.collect == true)
            R.drawable.ic_favorite else R.drawable.ic_unfavorite)

        itemView.mIvFavStatus.setColorFilter(ResUtils.getColorRes(R.color.colorAccent))
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.mIvFavStatus -> {
                mOnItemClickListener?.onItemClick(null, view, getAdapterPosition(), ONITEM_FAV_CLICK)
            }
            else -> {
                mOnItemClickListener?.onItemClick(null, view, getAdapterPosition(), ONITEM_CLICK)
            }

        }

    }
}