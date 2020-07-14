package com.victor.module.home.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
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
    private val viewmodel: GankViewModel by viewModels { GankViewModel.LiveDataVMFactory }
    private lateinit var adapter: GankAdapter
    
    override fun getLayoutResource(): Int {
        return R.layout.activity_gank
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCtlTitle.title = "Android"
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        adapter = GankAdapter()
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
                adapter.submitData(it)
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
}