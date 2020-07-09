package com.victor.module.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.cherry.viewmodel.HomeViewModel
import com.victor.cherry.viewmodel.LiveDataVMFactory
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeBinding

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
class HomeFragment: BaseFragment() {
    private val viewmodel: HomeViewModel by viewModels { LiveDataVMFactory }
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
        val binding = viewDataBinding as FragmentHomeBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }
}