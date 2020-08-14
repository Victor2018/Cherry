package com.victor.module.home.view.holder

import android.graphics.Typeface
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.app.App
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.module.home.R
import kotlinx.android.synthetic.main.rv_gank_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankViewHolder
 * Author: Victor
 * Date: 2020/7/14 下午 06:32
 * Description: 
 * -----------------------------------------------------------------
 */
class GankViewHolder (var view: View)
    : RecyclerView.ViewHolder(view) {
    private var post : GankDetailInfo? = null

    init {
        view.setOnClickListener {
            post?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
                WebActivity.intentStart(view.context,post?.title!!,post?.url!!)
            }
        }
    }

    fun bind(post: GankDetailInfo?) {
        this.post = post!!

        view.mTvTitle.setText(
            Html.fromHtml(
                ("<a href=\""
                        + post?.url) + "\">"
                        + post?.desc + "</a>" + "[" + post?.author
                    .toString() + "]"
            )
        )
        //使用WebActivity显示网页，屏蔽使用系统浏览器
//        mTvTitle.setMovementMethod(LinkMovementMethod.getInstance())

        if (post.images?.size!! > 0) {
            view.mIvPoster.visibility = View.VISIBLE
            ImageUtils.instance.loadImage(App.get(),view.mIvPoster,post.images?.get(0))
        } else {
            view.mIvPoster.visibility = View.GONE
        }

        view.mTvTime.text = post?.publishedAt

    }

    companion object {
        fun create(parent: ViewGroup,fontStyle: Typeface?): GankViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_gank_cell, parent, false)

            view.mTvTitle.typeface = fontStyle
            view.mTvTime.typeface = fontStyle

            return GankViewHolder(view)
        }
    }

    fun updateScore(item: GankDetailInfo?) {
        post = item
    }

}