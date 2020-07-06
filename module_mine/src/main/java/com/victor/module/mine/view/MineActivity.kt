package com.victor.module.mine.view

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.module.mine.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MineActivity
 * Author: Victor
 * Date: 2020/7/3 下午 06:45
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.MineAct)
class MineActivity: BaseActivity() {
    var currentFragment: Fragment? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_mine
    }

    override fun onResume() {
        super.onResume()
        switchFragment(currentFragment,MineFragment.newInstance())
    }

    fun switchFragment(fromFragment: Fragment?, toFragment: Fragment?) {
        if (currentFragment?.javaClass?.name == toFragment?.javaClass?.name) {
            return
        }

        currentFragment = toFragment

        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit)
        if (toFragment?.isAdded()!!) {
            ft.show(toFragment)
        } else {
            ft.add(R.id.fl_mine_container, toFragment)
        }
        if (fromFragment != null) {
            ft.hide(fromFragment)
        }
        ft.commitAllowingStateLoss()
    }
}