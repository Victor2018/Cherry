package com.victor.module.mine.view

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.module.mine.R

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

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }
}