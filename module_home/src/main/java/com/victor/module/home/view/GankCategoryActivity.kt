package com.victor.module.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.*
import com.victor.lib.coremodel.util.HttpUtil
import com.victor.module.home.R
import com.victor.module.home.view.adapter.GankCategoryAdapter
import kotlinx.android.synthetic.main.activity_gank_category.*
import org.victor.funny.util.ResUtils

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
class GankCategoryActivity: BaseActivity(),AdapterView.OnItemClickListener,View.OnClickListener {

    var gankCategoryAdapter: GankCategoryAdapter? = null

    companion object {
        const val SELECT_CATEGORY_REQUEST_CODE = 6
        const val SELECT_CATEGORY_KEY = "SELECT_CATEGORY_KEY"

        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, GankCategoryActivity::class.java)
            activity.startActivity(intent)
        }

        fun  intentStart (activity: AppCompatActivity, sharedElement: View,
                          sharedElementName: String) {
            var intent = Intent(activity, GankCategoryActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,sharedElement, sharedElementName)

            ActivityCompat.startActivity(activity!!, intent,options.toBundle())
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
        gankCategoryAdapter = GankCategoryAdapter(this,this)
        mRvGankCategory.setHasFixedSize(true)
        mRvGankCategory.adapter = gankCategoryAdapter

        mFabClose.setOnClickListener(this)

    }

    fun initData () {
        if (!HttpUtil.isNetEnable(App.get())) {
            SnackbarUtil.ShortSnackbar(mIvGirl,
                ResUtils.getStringRes(com.victor.lib.common.R.string.network_error),
                SnackbarUtil.ALERT
            )
            return
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var categoryRes = JsonUtils.toJSONString(gankCategoryAdapter?.getItem(position)!!)!!
        SharePreferencesUtil.putString(this,Constant.CATEGORY_TYPE_KEY,categoryRes)
        onBackPressed()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabClose -> {
                onBackPressed()
            }
        }
    }

}