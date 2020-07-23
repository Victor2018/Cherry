package com.victor.module.girls.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Loger
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.lib.coremodel.viewmodel.MineViewModel
import com.victor.module.girls.R
import com.victor.module.girls.view.adapter.GankGirlAdapter
import com.victor.module.girls.view.adapter.GankGirlLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_girls.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsFragment
 * Author: Victor
 * Date: 2020/7/15 下午 04:16
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.GirlsFgt)
class GirlsFragment: BaseFragment(),AdapterView.OnItemClickListener {
    private val viewmodel: MineViewModel by viewModels { MineViewModel.LiveDataVMFactory }

    private lateinit var adapter: GankGirlAdapter
    private var gridLayoutManager: GridLayoutManager? = null
    var datas: ArrayList<GankDetailInfo> = ArrayList()

    companion object {
        fun newInstance(): GirlsFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): GirlsFragment {
            val fragment = GirlsFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_girls
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
        initSwipeToRefresh()

    }

    private fun initAdapter() {
        gridLayoutManager = GridLayoutManager(context, 2) //这里用线性宫格显示 类似于grid view

        list.setLayoutManager(gridLayoutManager)

        adapter = GankGirlAdapter(this)
        list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GankGirlLoadStateAdapter(
                adapter
            ),
            footer = GankGirlLoadStateAdapter(
                adapter
            )
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                swipe_refresh.isRefreshing = loadStates.refresh is LoadState.Loading
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
                list.scrollToPosition(0)
            }
        }
    }

    private fun initSwipeToRefresh() {
        swipe_refresh.setOnRefreshListener {
            datas.clear()
            adapter.refresh()
        }
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Loger.e(TAG,"initData-onItemClick-gankDetailList?.size = " + datas?.size)
        Loger.e(TAG,"initData-onItemClick-adapter.itemCount = " + adapter.itemCount)
//        GirlsDetailActivity.intentStart(activity!! as AppCompatActivity,datas)
    }
}