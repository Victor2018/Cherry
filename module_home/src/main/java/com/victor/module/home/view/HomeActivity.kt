package com.victor.module.home.view

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.victor.lib.common.base.BaseActivity
import com.victor.module.home.R
import kotlinx.android.synthetic.main.activity_gank.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeActivity
 * Author: Victor
 * Date: 2020/7/3 下午 06:45
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeActivity: BaseActivity() {
    var currentFragment: Fragment? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_home
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
        switchFragment(currentFragment,HomeFragment.newInstance())
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
            ft.add(R.id.fl_home_container, toFragment)
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