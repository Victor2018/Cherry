package com.victor.module.tv.view

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.victor.lib.common.base.BaseActivity
import com.victor.module.tv.R
import kotlinx.android.synthetic.main.activity_tv.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvActivity
 * Author: Victor
 * Date: 2020/8/5 下午 03:00
 * Description: 
 * -----------------------------------------------------------------
 */
class TvActivity: BaseActivity() {
    var currentFragment: Fragment? = null
    override fun getLayoutResource(): Int {
        return R.layout.activity_tv
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
        switchFragment(currentFragment,TvFragment.newInstance())
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
            ft.add(R.id.fl_tv_container, toFragment)
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