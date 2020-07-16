package com.victor.module.girls.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.clips.util.AppUtil
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.girls.R
import com.victor.module.girls.view.adapter.GirlDetailAdapter
import kotlinx.android.synthetic.main.activity_girls_detail.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsDetailActivity
 * Author: Victor
 * Date: 2020/7/16 上午 09:59
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.GirlsDetailAct)
class GirlsDetailActivity: BaseActivity() {
    var girlDetailAdapter: GirlDetailAdapter? = null
    var gankDetailList: ArrayList<GankDetailInfo>? = null
    var gankDetailInfo: GankDetailInfo? = null

    companion object {
        open fun intentStart(ctx: Context?) {
            if (ctx == null) return
            val intent = Intent(ctx, GirlsDetailActivity::class.java)
            AppUtil.launchApp(ctx, intent)
        }
        fun  intentStart (activity: AppCompatActivity, datas: ArrayList<GankDetailInfo>) {
            var intent = Intent(activity, GirlsDetailActivity::class.java)
            intent.putExtra(NavigationUtils.GANK_DATA_KEY, datas)
            activity.startActivity(intent)
        }
        fun  intentStart (ctx: Context, data: GankDetailInfo) {
            var intent = Intent(ctx, GirlsDetailActivity::class.java)
            intent.putExtra(NavigationUtils.GANK_DATA_KEY, data)
            ctx.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_girls_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(intent)
        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_gank_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_share -> {
               /* val url: String = gankInfos.get(currentPage).getUrl()
                val intentshare = Intent(Intent.ACTION_SEND)
                intentshare.setType(Constant.SHARE_TYPE)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                    .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_beauty) + url)
                Intent.createChooser(intentshare, getString(R.string.share))
                startActivity(intentshare)*/
                return true
            }
            R.id.action_save -> {
                return true
            }
            R.id.action_set_wallpaper -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initData(intent)
        initialize()
    }

    fun initialize () {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        girlDetailAdapter = GirlDetailAdapter(this,gankDetailList!!)
//        mVpGirls.adapter = girlDetailAdapter
    }

    fun initData (intent: Intent?) {
//        gankDetailList = intent?.getSerializableExtra(NavigationUtils.GANK_DATA_KEY) as ArrayList<GankDetailInfo>?
        gankDetailInfo = intent?.getSerializableExtra(NavigationUtils.GANK_DATA_KEY) as GankDetailInfo?
        Loger.e(TAG,"initData-gankDetailList?.size = " + gankDetailList?.size)

        ImageUtils.instance.loadImage(this,mIvGirl,gankDetailInfo?.images?.get(0))
    }
}