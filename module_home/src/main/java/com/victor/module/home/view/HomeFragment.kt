package com.victor.module.home.view

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.cherry.viewmodel.HomeViewModel
import com.victor.cherry.viewmodel.LiveDataVMFactory
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.view.widget.cardslider.CardSliderLayoutManager
import com.victor.lib.common.view.widget.cardslider.CardSnapHelper
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeBinding
import com.victor.module.home.view.adapter.HomeAdapter
import com.victor.module.home.view.widget.DescriptionViewSwitcherFactory
import com.victor.module.home.view.widget.TitleViewSwitcherFactory
import kotlinx.android.synthetic.main.fragment_home.*
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
@Route(path = ARouterPath.HomeFgt)
class HomeFragment: BaseFragment(),AdapterView.OnItemClickListener {
    private val viewmodel: HomeViewModel by viewModels { LiveDataVMFactory }

    var homeAdapter: HomeAdapter? = null
    var layoutManager: CardSliderLayoutManager? = null
    private var currentPosition = 0

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
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
        layoutManager = CardSliderLayoutManager(activity!!)
        mRvGank.setHasFixedSize(true)
        mRvGank.layoutManager = layoutManager
        mRvGank.adapter = homeAdapter

        mRvGank.setOnFlingListener(null);
        CardSnapHelper().attachToRecyclerView(mRvGank)

        mRvGank.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange()
                }
            }
        })
    }

    fun initData () {
        viewmodel.gankData.observe(viewLifecycleOwner, Observer {
            homeAdapter?.clear()
            homeAdapter?.add(it.data)
            homeAdapter?.notifyDataSetChanged()

            mTsDescription.setText(homeAdapter?.getItem(0)?.desc)
        })
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        ToastUtils.showShort(homeAdapter?.getItem(position)?.desc!!)
        NavigationUtils.goGankActivity(homeAdapter?.getItem(position)?.title!!,homeAdapter?.getItem(position)?.type!!)
    }

    private fun onActiveCardChange() {
        val pos: Int = layoutManager?.getActiveCardPosition()!!
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return
        }
        onActiveCardChange(pos)
    }

    private fun onActiveCardChange(pos: Int) {
        mTsDescription.setText(homeAdapter?.getItem(pos)?.desc)
        currentPosition = pos
    }

}