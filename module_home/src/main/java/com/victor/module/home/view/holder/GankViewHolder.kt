package com.victor.module.home.view.holder

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
    private val mTvTitle: TextView = view.findViewById(R.id.title)
    private val mIvImage: ImageView = view.findViewById(R.id.image)
    private val mTvWho: TextView = view.findViewById(R.id.who)
    private val mTvTime : TextView = view.findViewById(R.id.time)
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
        mTvWho.text = itemView.context.resources.getString(R.string.subtitle,
            post?.author ?: "unknown")
        if (post?.url?.startsWith("http") == true) {
            mIvImage.visibility = View.VISIBLE
            ImageUtils.instance.loadImage(App.get(),mIvImage,post.url)
        } else {
            mIvImage.visibility = View.GONE
        }
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