package com.victor.module.mine.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.clips.util.AppUtil
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.view.activity.WebActivity
import com.victor.module.mine.R
import kotlinx.android.synthetic.main.fragment_mine.*

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
class MineFragment: BaseFragment(),View.OnClickListener {
    var fontStyle: Typeface? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialize()
        initData()
    }

    fun initialize () {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        mTvDownloadApp.movementMethod = LinkMovementMethod.getInstance()
        mTvGmail.movementMethod = LinkMovementMethod.getInstance()
        mTvIssues.movementMethod = LinkMovementMethod.getInstance()

        mFabGitHub.setOnClickListener(this)

        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")
        mTvDescription.typeface = fontStyle
        mTvDownloadApp.typeface = fontStyle
        mTvGmail.typeface = fontStyle
        mTvIssues.typeface = fontStyle
        mTvVersion.typeface = fontStyle
        mTvSupport.typeface = fontStyle
    }

    fun initData () {
        mTvVersion.text = String.format("Version：v%s",AppUtil.getAppVersionName(context!!))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabGitHub -> {
                WebActivity.intentStart(activity as AppCompatActivity, getString(R.string.github), getString(R.string.github_url), false)
            }
        }
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

}

