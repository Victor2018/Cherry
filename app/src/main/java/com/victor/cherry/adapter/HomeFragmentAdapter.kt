package com.victor.cherry.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.victor.lib.common.base.BaseFragment

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeFragmentAdapter
 * Author: Victor
 * Date: 2020/7/13 下午 03:38
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeFragmentAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    var fragmetList: List<BaseFragment>? = null
    override fun getItem(position: Int): Fragment {
        return fragmetList?.get(position)!!
    }

    override fun getCount(): Int {
        return fragmetList?.size!!
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }
}