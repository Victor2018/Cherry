package com.victor.module.home.view.holder

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.app.App
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.home.R

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
class GankViewHolder (view: View)
    : RecyclerView.ViewHolder(view) {
    private val mTvTitle: TextView = view.findViewById(R.id.tv_title)
    private val mIvImage: ImageView = view.findViewById(R.id.iv_image)
    private val mTvTime : TextView = view.findViewById(R.id.tv_time)
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
        mTvTitle.setText(
            Html.fromHtml(
                ("<a href=\""
                        + post?.url) + "\">"
                        + post?.desc + "</a>" + "[" + post?.author
                    .toString() + "]"
            )
        )
        //使用WebActivity显示网页，屏蔽使用系统浏览器
//        mTvTitle.setMovementMethod(LinkMovementMethod.getInstance())

        if (post?.url?.startsWith("http") == true) {
            mIvImage.visibility = View.VISIBLE
            ImageUtils.instance.loadImage(App.get(),mIvImage,post.url)
        } else {
            mIvImage.visibility = View.GONE
        }

        mTvTime.text = post?.publishedAt

    }

    companion object {
        fun create(parent: ViewGroup): GankViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_gank_cell, parent, false)
            return GankViewHolder(view)
        }
    }

    fun updateScore(item: GankDetailInfo?) {
        post = item
    }
}