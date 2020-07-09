package com.victor.module.mine.view.holder

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
import com.victor.lib.coremodel.entity.GirlInfo
import com.victor.module.mine.R

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
    private val title: TextView = view.findViewById(R.id.title)
    private val subtitle: TextView = view.findViewById(R.id.subtitle)
    private val score: TextView = view.findViewById(R.id.score)
    private val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
    private var post : GirlInfo? = null
    init {
        view.setOnClickListener {
            post?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(post: GirlInfo?) {
        this.post = post!!
        title.text = post?.title ?: "loading"
        subtitle.text = itemView.context.resources.getString(R.string.post_subtitle,
            post?.author ?: "unknown")
        score.text = "${post?.views ?: 0}"
        if (post?.url?.startsWith("http") == true) {
            thumbnail.visibility = View.VISIBLE
            ImageUtils.instance.loadImage(App.get(),thumbnail,post.url)
        } else {
            thumbnail.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): GankGirlViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reddit_post_item, parent, false)
            return GankGirlViewHolder(view)
        }
    }

    fun updateScore(item: GirlInfo?) {
        post = item
        score.text = "${item?.views ?: 0}"
    }
}