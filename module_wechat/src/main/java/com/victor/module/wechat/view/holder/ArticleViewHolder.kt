package com.victor.module.wechat.view.holder

import android.graphics.Typeface
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.coremodel.data.ArticleInfo
import com.victor.module.wechat.R
import kotlinx.android.synthetic.main.rv_article_cell.view.*

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
class ArticleViewHolder (var view: View)
    : RecyclerView.ViewHolder(view) {
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
        view.mTvTitle.setText(
            Html.fromHtml(
                ("<a href=\""
                        + post?.link) + "\">"
                        + post?.title + "</a>" + "[" + post?.author
                    .toString() + "]"
            )
        )
        //使用WebActivity显示网页，屏蔽使用系统浏览器
//        mTvTitle.setMovementMethod(LinkMovementMethod.getInstance())

        view.mTvTime.text = post?.niceShareDate

    }

    companion object {
        fun create(parent: ViewGroup,fontStyle: Typeface?): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_article_cell, parent, false)

            view.mTvTitle.typeface = fontStyle
            view.mTvTime.typeface = fontStyle

            return ArticleViewHolder(view)
        }
    }

    fun updateScore(item: ArticleInfo?) {
        post = item
    }
}