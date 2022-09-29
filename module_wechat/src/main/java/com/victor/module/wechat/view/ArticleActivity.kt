package com.victor.module.wechat.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.coremodel.util.Loger
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.coremodel.data.ArticleInfo
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.viewmodel.ArticleViewModel
import com.victor.module.wechat.R
import com.victor.module.wechat.view.adapter.ArticleAdapter
import com.victor.module.wechat.view.adapter.ArticleLoadStateAdapter
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArticleActivity
 * Author: Victor
 * Date: 2020/7/23 上午 10:56
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.ArticleAct)
class ArticleActivity: BaseActivity() {
    companion object {
        fun  intentStart (activity: AppCompatActivity, type: String) {
            var intent = Intent(activity, ArticleActivity::class.java)
            intent.putExtra(NavigationUtils.TYPE_KEY, type)
            activity.startActivity(intent)
        }
    }
    private lateinit var adapter: ArticleAdapter

    var title: String? = null
    var id: Int = 0

    private val viewmodel: ArticleViewModel by viewModels {
        InjectorUtils.provideArticleVMFactory(id,this,this)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_article
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(intent)
        initialize()
    }

    fun initialize () {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initAdapter()
        initSwipeToRefresh()

        toolbar.title = title
        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlArticle.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    fun initData (intent: Intent?) {
        title = intent?.getStringExtra(NavigationUtils.TITLE_KEY)
        id = intent?.getIntExtra(NavigationUtils.ID_KEY,0)!!;
        Loger.e(TAG,"id = " + id)
    }

    private fun initAdapter() {
        adapter = ArticleAdapter(this)
        mRvArticle.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArticleLoadStateAdapter(
                adapter
            ),
            footer = ArticleLoadStateAdapter(
                adapter
            )
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                mSrlArticle.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewmodel.datas.collectLatest {
                adapter.submitData(it as PagingData<ArticleInfo>)
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
            adapter.dataRefreshFlow.collectLatest {
                mRvArticle.scrollToPosition(0)
            }
        }
    }

    private fun initSwipeToRefresh() {
        mSrlArticle.setOnRefreshListener { adapter.refresh() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initData(intent)
        initialize()
    }
}