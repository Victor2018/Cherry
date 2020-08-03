package com.victor.module.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.cherry.viewmodel.GankCategoryLiveDataVMFactory
import com.victor.cherry.viewmodel.GankCategoryViewModel
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.module.home.R
import com.victor.module.home.databinding.ActivityGankCategoryBinding
import com.victor.module.home.view.adapter.GankCategoryAdapter
import kotlinx.android.synthetic.main.activity_gank_category.*
import kotlinx.android.synthetic.main.fragment_home.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankCategoryActivity
 * Author: Victor
 * Date: 2020/8/1 下午 05:18
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.GankCategoryAct)
class GankCategoryActivity: BaseActivity(),AdapterView.OnItemClickListener {
    private val viewmodel: GankCategoryViewModel by viewModels { GankCategoryLiveDataVMFactory }
    var gankCategoryAdapter: GankCategoryAdapter? = null
    companion object {
        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, GankCategoryActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_gank_category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        initData()
    }

    fun initialize () {
        val binding = viewDataBinding as ActivityGankCategoryBinding

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel

        gankCategoryAdapter = GankCategoryAdapter(this,this)
        mRvGankCategory.setHasFixedSize(true)
        mRvGankCategory.adapter = gankCategoryAdapter
    }

    fun initData () {
        viewmodel.gankData.observe(this, Observer {
            it.let {
                gankCategoryAdapter?.clear()
                gankCategoryAdapter?.add(it.data)
                gankCategoryAdapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}