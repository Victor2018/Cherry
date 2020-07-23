package com.victor.module.wechat.view.holder

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.coremodel.entity.ArticleInfo
import com.victor.module.wechat.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArticleViewHolder
 * Author: Victor
 * Date: 2020/7/23 上午 11:01
 * Description: 
 * -----------------------------------------------------------------
 */
class ArticleViewHolder (view: View)
    : RecyclerView.ViewHolder(view) {
    private val mTvTitle: TextView = view.findViewById(R.id.tv_title)
//    private val mIvImage: ImageView = view.findViewById(R.id.iv_image)
    private val mTvTime : TextView = view.findViewById(R.id.tv_time)
    private var post : ArticleInfo? = null
    init {
        view.setOnClickListener {
            post?.link?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
                WebActivity.intentStart(view.context,post?.title!!,post?.link!!)
            }
        }
    }

    fun bind(post: ArticleInfo?) {
        this.post = post!!
        mTvTitle.setText(
            Html.fromHtml(
                ("<a href=\""
                        + post?.link) + "\">"
                        + post?.title + "</a>" + "[" + post?.author
                    .toString() + "]"
            )
        )
        //使用WebActivity显示网页，屏蔽使用系统浏览器
//        mTvTitle.setMovementMethod(LinkMovementMethod.getInstance())

        mTvTime.text = post?.niceShareDate

    }

    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_article_cell, parent, false)
            return ArticleViewHolder(view)
        }
    }

    fun updateScore(item: ArticleInfo?) {
        post = item
    }
}