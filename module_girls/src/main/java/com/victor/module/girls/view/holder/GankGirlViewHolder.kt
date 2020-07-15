package com.victor.module.girls.view.holder

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.app.App
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.girls.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlViewHolder
 * Author: Victor
 * Date: 2020/7/8 下午 05:29
 * Description: A RecyclerView ViewHolder that displays a reddit post.
 * -----------------------------------------------------------------
 */
class GankGirlViewHolder (view: View)
    : RecyclerView.ViewHolder(view) {
    private val mTvTitle: TextView = view.findViewById(R.id.tv_title)
    private val mIvImg : ImageView = view.findViewById(R.id.iv_img)
    private var post : GankDetailInfo? = null
    init {
        view.setOnClickListener {
            post?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(post: GankDetailInfo?) {
        this.post = post!!
        mTvTitle.text = post?.desc ?: "loading"
        ImageUtils.instance.loadImage(App.get(),mIvImg,post.url)
    }

    companion object {
        fun create(parent: ViewGroup): GankGirlViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_girls_cell, parent, false)
            return GankGirlViewHolder(view)
        }
    }

    fun updateScore(item: GankDetailInfo?) {
        post = item
    }
}