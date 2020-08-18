package com.victor.module.girls.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.data.RepositoryType
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.viewmodel.ArticleViewModel
import com.victor.lib.coremodel.viewmodel.GirlsViewModel
import com.victor.module.girls.R
import com.victor.module.girls.view.adapter.GirlsAdapter
import com.victor.module.girls.view.adapter.GirlsLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_girls.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.victor.funny.util.ResUtils

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
class GirlsFragment: BaseFragment(),AdapterView.OnItemClickListener,Toolbar.OnMenuItemClickListener{
//    private val viewmodel: GirlsViewModel by viewModels { GirlsLiveDataVMFactory }

    private val viewmodel: GirlsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GirlsViewModel(
                    ServiceLocator.instance().getRepository(RepositoryType.GANK_GIRL,activity!!)) as T
            }
        }
    }

    private lateinit var girlsAdapter: GirlsAdapter

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

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialize()
    }

    fun initialize () {
        setHasOptionsMenu(true)
        var textView: TextView = toolbar.getChildAt(0) as TextView//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT//填充父类
        textView.setGravity(Gravity.CENTER_VERTICAL)//水平居中，CENTER，即水平也垂直，自选

        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_girls)
        toolbar.setOnMenuItemClickListener(this)

        mRvGirls.setHasFixedSize(true)

        initAdapter()

        initSwipeToRefresh()
    }

    private fun initAdapter() {
        girlsAdapter = GirlsAdapter(this)
        mRvGirls.adapter = girlsAdapter.withLoadStateHeaderAndFooter(
            header = GirlsLoadStateAdapter(
                girlsAdapter
            ),
            footer = GirlsLoadStateAdapter(
                girlsAdapter
            )
        )
        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            girlsAdapter.loadStateFlow.collectLatest { loadStates ->
                swipe_refresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewmodel.datas.collectLatest {
                girlsAdapter.submitData(it as PagingData<GankDetailInfo>)
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
            girlsAdapter.dataRefreshFlow.collectLatest {
                mRvGirls.scrollToPosition(0)
            }
        }
    }

    private fun initSwipeToRefresh() {
        swipe_refresh.setOnRefreshListener { girlsAdapter.refresh() }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        GirlsDetailActivity.intentStart(activity!! as AppCompatActivity, position,
        view?.findViewById(R.id.iv_img),ResUtils.getStringRes(R.string.transition_girl_img))
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_share -> {
                var intentshare = Intent(Intent.ACTION_SEND);
                intentshare.setType(Constant.SHARE_TYPE)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                    .putExtra(Intent.EXTRA_TEXT,getString(R.string.share_app));
                Intent.createChooser(intentshare, getString(R.string.share));
                startActivity(intentshare);
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

}