package com.victor.module.mine.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.view.activity.WebActivity
import com.victor.module.mine.R
import com.victor.player.library.util.AppUtil
import kotlinx.android.synthetic.main.activity_about.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AboutActivity
 * Author: Victor
 * Date: 2020/8/20 上午 09:56
 * Description: 
 * -----------------------------------------------------------------
 */

class AboutActivity: BaseActivity(), View.OnClickListener {
    var fontStyle: Typeface? = null

    companion object {
        const val SELECT_CATEGORY_REQUEST_CODE = 6
        const val SELECT_CATEGORY_KEY = "SELECT_CATEGORY_KEY"

        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }

        fun  intentStart (activity: AppCompatActivity, sharedElement: View,
                          sharedElementName: String) {
            var intent = Intent(activity, AboutActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,sharedElement, sharedElementName)

            ActivityCompat.startActivity(activity!!, intent,options.toBundle())
        }
    }

    override fun getLayoutResource() = R.layout.activity_about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mTvDownloadApp.movementMethod = LinkMovementMethod.getInstance()
        mTvGmail.movementMethod = LinkMovementMethod.getInstance()
        mTvIssues.movementMethod = LinkMovementMethod.getInstance()

        mFabGitHub.setOnClickListener(this)

        fontStyle = Typeface.createFromAsset(assets, "fonts/zuo_an_lian_ren.ttf")
        mTvDescription.typeface = fontStyle
        mTvDownloadApp.typeface = fontStyle
        mTvGmail.typeface = fontStyle
        mTvIssues.typeface = fontStyle
        mTvVersion.typeface = fontStyle
        mTvSupport.typeface = fontStyle
    }

    fun initData () {
        mTvVersion.text = String.format("Version：v%s", AppUtil.getAppVersionName(this))
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabGitHub -> {
                WebActivity.intentStart(this, getString(R.string.github), getString(R.string.github_url), false)
            }
        }
    }
}