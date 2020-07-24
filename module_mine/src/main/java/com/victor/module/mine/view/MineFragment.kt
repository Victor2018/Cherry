package com.victor.module.mine.view

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.mine.R
import com.victor.lib.coremodel.viewmodel.MineViewModel
import com.victor.module.mine.view.adapter.GankGirlAdapter
import com.victor.module.mine.view.adapter.GankGirlLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MineFragment
 * Author: Victor
 * Date: 2020/7/3 下午 06:35
 * Description:
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.MineFgt)
class MineFragment: BaseFragment() {

    private val viewmodel: MineViewModel by viewModels { MineViewModel.LiveDataVMFactory }

    private lateinit var adapter: GankGirlAdapter

    companion object {
        fun newInstance(): MineFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_mine
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialize()

    }

    fun initialize () {
        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        var textView: TextView = toolbar.getChildAt(0) as TextView;//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;//填充父类
        textView.setGravity(Gravity.CENTER_HORIZONTAL);//水平居中，CENTER，即水平也垂直，自选

        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        adapter = GankGirlAdapter()
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
        swipe_refresh.setOnRefreshListener { adapter.refresh() }
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

}

