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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.cherry.viewmodel.HomeViewModel
import com.victor.cherry.viewmodel.LiveDataVMFactory
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeBinding
import com.victor.module.home.view.adapter.HomeAdapter
import com.victor.module.home.view.widget.BannerSwitcherView
import com.victor.module.home.view.widget.DescriptionViewSwitcherFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.toolbar


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
    Toolbar.OnMenuItemClickListener,View.OnClickListener, LMRecyclerView.OnLoadMoreListener {
    private val viewmodel: HomeViewModel by viewModels { LiveDataVMFactory }

    var homeAdapter: HomeAdapter? = null
    var currentPage = 1
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


        val binding = viewDataBinding as FragmentHomeBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel

        if (mTsDescription.childCount < 2) {
            mTsDescription.setFactory(DescriptionViewSwitcherFactory(context!!))
        }

        mTsDescription.setInAnimation(context!!, android.R.anim.fade_in)
        mTsDescription.setOutAnimation(context!!, android.R.anim.fade_out)

        homeAdapter = HomeAdapter(context!!,this)
        homeAdapter?.setHeaderVisible(false)
        homeAdapter?.setFooterVisible(false)
        mRvGank.setHasFixedSize(true)
        mRvGank.adapter = homeAdapter
        mRvGank.setLoadMoreListener(this)

        mBsvBanner.onItemClickListener = this
        mFabGankCategory.setOnClickListener(this)
    }

    fun initData () {
        viewmodel.bannerData.observe(viewLifecycleOwner, Observer {
            it.let {
                mBsvBanner.startWithList(it.data)
            }
        })
        viewmodel.searchGankDetail("Android",currentPage)
        viewmodel.gankDetailValue.observe(viewLifecycleOwner, Observer {
            it.let {it1 ->
                it.data.let {it2 ->
                    if (currentPage == 1) {
                        homeAdapter?.clear()
                    }
                    homeAdapter?.setFooterVisible(it.page < it.page_count)
                    if (it.page < it.page_count) {
                        homeAdapter?.setLoadState(homeAdapter?.LOADING!!)
                    } else {
                        homeAdapter?.setLoadState(homeAdapter?.LOADING_END!!)
                    }

                    homeAdapter?.add(it.data)
                    homeAdapter?.notifyDataSetChanged()
                }
            }

        })
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (id == BannerSwitcherView.ON_BANNER_ITEM_CLICK) {
            WebActivity.intentStart(activity!!,mBsvBanner?.messages?.get(position)?.title!!,
                mBsvBanner?.messages?.get(position)?.url!!)
        } else if (id == BannerSwitcherView.ON_BANNER_ITEM_SELECT) {
            mTsDescription.setText(mBsvBanner?.messages?.get(position)?.title)
        } else {
            NavigationUtils.goGankActivity(homeAdapter?.getItem(position)?.title!!,homeAdapter?.getItem(position)?.type!!)
        }
    }



    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_search -> {
                NavigationUtils.goSearchGankActivity()
                return true
            }
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabGankCategory -> {
                GankCategoryActivity.intentStart(activity!! as AppCompatActivity)
            }
        }
    }

    override fun OnLoadMore() {
        TODO("Not yet implemented")
    }


}