package com.victor.module.tv.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Loger
import com.victor.lib.common.view.widget.cardslider.CardSliderLayoutManager
import com.victor.lib.common.view.widget.cardslider.CardSnapHelper
import com.victor.module.tv.R
import com.victor.module.tv.data.ChannelCategory
import com.victor.module.tv.databinding.FragmentTvBinding
import com.victor.module.tv.view.adapter.TvAdapter
import com.victor.module.tv.viewmodel.LiveDataVMFactory
import com.victor.module.tv.viewmodel.TvViewModel
import kotlinx.android.synthetic.main.fragment_tv.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvFragment
 * Author: Victor
 * Date: 2020/7/24 上午 11:02
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.TvFgt)
class TvFragment: BaseFragment(), AdapterView.OnItemClickListener {
    private val viewmodel: TvViewModel by viewModels { LiveDataVMFactory }
    var tvAdapter: TvAdapter? = null

    companion object {
        fun newInstance(): TvFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TvFragment {
            val fragment = TvFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_tv
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        val binding = viewDataBinding as FragmentTvBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel

        tvAdapter = TvAdapter(activity!!,this)
        mRvChannels.setHasFixedSize(true)
        mRvChannels.adapter = tvAdapter
    }

    fun initData () {
        viewmodel.tvData.observe(viewLifecycleOwner, Observer {
            Loger.e(TAG,"initData-channel_category = " + it.count)
            Loger.e(TAG,"initData-channels = " + it.categorys)
            tvAdapter?.clear()
            tvAdapter?.add(it.categorys)
            tvAdapter?.notifyDataSetChanged()

        })

    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}