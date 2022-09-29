package com.victor.module.home.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.*
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.common.view.widget.banner.BannerViewFlipper
import com.victor.lib.common.view.widget.banner.DescriptionViewSwitcherFactory
import com.victor.lib.coremodel.data.*
import com.victor.lib.coremodel.vm.HomeVM
import com.victor.lib.coremodel.vm.factory.HomeVMFactory
import com.victor.module.home.R
import com.victor.module.home.view.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.toolbar
import org.victor.funny.util.ResUtils
import org.victor.funny.util.ToastUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeFragment
 * Author: Victor
 * Date: 2020/7/3 下午 06:35
 * Description: 
 * -----------------------------------------------------------------
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@Route(path = ARouterPath.HomeFgt)
class HomeFragment: BaseFragment(),AdapterView.OnItemClickListener,
    Toolbar.OnMenuItemClickListener,View.OnClickListener, LMRecyclerView.OnLoadMoreListener,
    AppBarLayout.OnOffsetChangedListener,SwipeRefreshLayout.OnRefreshListener {

    private lateinit var homeVM: HomeVM

    var homeAdapter: HomeAdapter? = null
    var currentPage = 0
    var type: String? = "Android"

    companion object {
        fun newInstance(): HomeFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setHasOptionsMenu(true)
        var textView: TextView = toolbar.getChildAt(0) as TextView//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT//填充父类
        textView.setGravity(Gravity.CENTER_VERTICAL)//水平居中，CENTER，即水平也垂直，自选

        mCtlTitle.title = type

        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_home)
        toolbar.setOnMenuItemClickListener(this)

        homeVM = ViewModelProvider(this, HomeVMFactory(this))[HomeVM::class.java]

        subscribeUi()

        if (mTsDescription.childCount < 2) {
            mTsDescription.setFactory(DescriptionViewSwitcherFactory(context!!))
        }

        mTsDescription.setInAnimation(context!!, android.R.anim.fade_in)
        mTsDescription.setOutAnimation(context!!, android.R.anim.fade_out)

        homeAdapter = HomeAdapter(context!!,this)
        mRvGank.setHasFixedSize(true)
        mRvGank.adapter = homeAdapter
        mRvGank.setLoadMoreListener(this)

        mBsvBanner.onItemClickListener = this
        mFabGankCategory.setOnClickListener(this)
        appbar.addOnOffsetChangedListener(this)

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlRefresh.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color.holo_red_light)

        mSrlRefresh.setOnRefreshListener(this)

        SharePreferencesUtil.putString(activity!!,Constant.CATEGORY_TYPE_KEY,"")
    }

    fun initData () {
        sendHomeBannerRequest()
        sendHomeSquareRequest()
    }

    fun sendHomeBannerRequest () {
        homeVM.fetchHomeBanner()
    }

    fun sendHomeSquareRequest () {
        homeVM.fetchHomeSquare(currentPage)
    }

    fun subscribeUi() {
        homeVM.homeBannerData.observe(viewLifecycleOwner, Observer {
            mSrlRefresh.isRefreshing = false
            when (it) {
                is HttpResult.Success -> {
                    showBannerData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.showShort(it.message.toString())
                }
            }
        })

        homeVM.homeSquareData.observe(viewLifecycleOwner, Observer {
            mSrlRefresh.isRefreshing = false
            when (it) {
                is HttpResult.Success -> {
                    showSquareData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.showShort(it.message.toString())
                }
            }
        })
    }

    fun showBannerData (data: BaseReq<List<HomeBannerInfo>>) {
        mBsvBanner.startWithList(data.data as ArrayList<HomeBannerInfo>?)
    }

    fun showSquareData (data: BaseReq<ListData<HomeSquareInfo>>) {
        homeAdapter?.showData(data.data?.datas,null,mRvGank,currentPage)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (id) {
            BannerViewFlipper.ON_BANNER_ITEM_CLICK -> {
                WebActivity.intentStart(activity!!,mBsvBanner?.messages?.get(position)?.title!!,
                    mBsvBanner?.messages?.get(position)?.url!!)
            }
            BannerViewFlipper.ON_BANNER_ITEM_SELECT -> {
                mTsDescription.setText(mBsvBanner?.messages?.get(position)?.title)
            }
            else -> {
                WebActivity.intentStart(activity!!,homeAdapter?.getItem(position)?.title!!,
                    homeAdapter?.getItem(position)?.link!!)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_search -> {
                NavigationUtils.goSearchGankActivity()
                return true
            }
            R.id.action_share -> {
                var intentshare = Intent(Intent.ACTION_SEND)
                intentshare.setType(Constant.SHARE_TYPE)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                    .putExtra(Intent.EXTRA_TEXT,getString(R.string.share_app))
                Intent.createChooser(intentshare, getString(R.string.share))
                startActivity(intentshare)
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabGankCategory -> {
                GankCategoryActivity.intentStart(activity as AppCompatActivity,
                    mFabGankCategory,ResUtils.getStringRes(R.string.transition_fab))
            }
        }
    }

    override fun OnLoadMore() {
        currentPage++
        sendHomeSquareRequest()
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

    override fun onRefresh() {
        mSrlRefresh.isRefreshing = true
        currentPage = 0
        homeAdapter?.clear()
        homeAdapter?.notifyDataSetChanged()

        initData()
    }

}