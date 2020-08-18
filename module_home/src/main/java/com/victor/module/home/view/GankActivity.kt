package com.victor.module.home.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.viewmodel.GankViewModel
import com.victor.module.home.R
import com.victor.module.home.view.adapter.GankAdapter
import com.victor.module.home.view.adapter.GankLoadStateAdapter
import kotlinx.android.synthetic.main.activity_gank.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankActivity
 * Author: Victor
 * Date: 2020/7/14 下午 05:19
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.GankAct)
class GankActivity: BaseActivity() {

    companion object {
        fun  intentStart (activity: AppCompatActivity, type: String) {
            var intent = Intent(activity, GankActivity::class.java)
            intent.putExtra(NavigationUtils.TYPE_KEY, type)
            activity.startActivity(intent)
        }
    }
    private lateinit var adapter: GankAdapter

    var title: String? = null
    var type: String? = null

    private val viewmodel: GankViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GankViewModel(type!!,
                    ServiceLocator.instance().getRepository(RepositoryType.GANK_DETAIL,this@GankActivity)) as T
            }
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_gank
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
        mSrlGank.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    fun initData (intent: Intent?) {
        title = intent?.getStringExtra(NavigationUtils.TITLE_KEY);
        type = intent?.getStringExtra(NavigationUtils.TYPE_KEY);
        Loger.e(TAG,"type = " + type)
    }

    private fun initAdapter() {
        adapter = GankAdapter(this)
        mRvGank.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GankLoadStateAdapter(
                adapter
            ),
            footer = GankLoadStateAdapter(
                adapter
            )
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                mSrlGank.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewmodel.datas.collectLatest {
                adapter.submitData(it as PagingData<GankDetailInfo>)
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
            adapter.dataRefreshFlow.collectLatest {
                mRvGank.scrollToPosition(0)
            }
        }
    }

    private fun initSwipeToRefresh() {
        mSrlGank.setOnRefreshListener { adapter.refresh() }
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