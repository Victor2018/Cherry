package com.victor.module.mine.view

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.module.mine.R
import kotlinx.android.synthetic.main.activity_mine.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun initialize () {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        ft.setCustomAnimations(com.victor.lib.common.R.anim.anim_fragment_enter,
            com.victor.lib.common.R.anim.anim_fragment_exit)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}