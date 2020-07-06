package com.victor.module.home.view

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.home.R

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
}