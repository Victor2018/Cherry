package com.victor.module.girls.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.google.android.material.appbar.AppBarLayout
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.*
import com.victor.lib.coremodel.util.HttpUtil
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
class GirlsFragment: BaseFragment(),AdapterView.OnItemClickListener,Toolbar.OnMenuItemClickListener,
    View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

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
        fetchRandomGirlData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }

    fun initialize () {
//        setHasOptionsMenu(true)
//        var textView: TextView = toolbar.getChildAt(0) as TextView//主标题
//        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT//填充父类
//        textView.setGravity(Gravity.CENTER_VERTICAL)//水平居中，CENTER，即水平也垂直，自选
//
//        toolbar.menu.clear()
//        toolbar.inflateMenu(R.menu.menu_girls)
//        toolbar.setOnMenuItemClickListener(this)

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlRefresh.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color.holo_red_light)

        mFabRandomGirl.setOnClickListener(this)
        appbar.addOnOffsetChangedListener(this)


        mRvGirls.setHasFixedSize(true)

        initAdapter()

        initSwipeToRefresh()

        subscribeUi()
    }

    private fun subscribeUi() {
    }

    private fun fetchRandomGirlData() {
        if (!HttpUtil.isNetEnable(App.get())) {
            SnackbarUtil.ShortSnackbar(mIvRandomGirl,
                ResUtils.getStringRes(com.victor.lib.common.R.string.network_error),
                SnackbarUtil.ALERT
            )
            return
        }
        showFreshGirlAnim()
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
                mSrlRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            if (!HttpUtil.isNetEnable(App.get())) {
                SnackbarUtil.ShortSnackbar(mIvRandomGirl,
                    ResUtils.getStringRes(com.victor.lib.common.R.string.network_error),
                    SnackbarUtil.ALERT
                )
                return@launchWhenCreated
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
        mSrlRefresh.setOnRefreshListener { girlsAdapter.refresh() }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        GirlsDetailActivity.intentStart(activity as AppCompatActivity, position,
        view?.findViewById(R.id.iv_img),
            ResUtils.getStringRes(com.victor.lib.common.R.string.transition_girl_img))
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_share -> {
                var intentshare = Intent(Intent.ACTION_SEND)
                intentshare.setType(Constant.SHARE_TYPE)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(com.victor.lib.common.R.string.share))
                    .putExtra(Intent.EXTRA_TEXT,getString(com.victor.lib.common.R.string.share_app))
                Intent.createChooser(intentshare, getString(com.victor.lib.common.R.string.share))
                startActivity(intentshare)
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mFabRandomGirl -> {
                fetchRandomGirlData()
            }
        }
    }

    private fun showFreshGirlAnim () {
        mFabRandomGirl.startAnimation(AnimUtil.xShake())
        mFabRandomGirl.isEnabled = false
    }
    private fun hideFreshGirlAnim () {
        mFabRandomGirl.isEnabled = true
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            //展开状态
            mSrlRefresh.isEnabled = true
        } else if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
            //折叠状态
            mSrlRefresh.isEnabled = false
        } else {
            //中间状态
        }
    }

}