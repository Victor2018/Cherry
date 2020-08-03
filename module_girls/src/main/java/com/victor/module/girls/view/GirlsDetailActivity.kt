package com.victor.module.girls.view

import android.Manifest
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.clips.util.AppUtil
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.*
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.girls.R
import com.victor.module.girls.view.adapter.GirlDetailAdapter
import kotlinx.android.synthetic.main.activity_girls_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
    var mWallpaperManager: WallpaperManager? = null

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
        fun  intentStart (activity: AppCompatActivity, data: GankDetailInfo, sharedElement: View,
                          sharedElementName: String) {

            var intent = Intent(activity, GirlsDetailActivity::class.java)
            intent.putExtra(NavigationUtils.GANK_DATA_KEY, data)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,sharedElement, sharedElementName)
            ActivityCompat.startActivity(activity!!, intent, options.toBundle())
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
                val url: String = gankDetailInfo?.images?.get(0)!!
                val intentshare = Intent(Intent.ACTION_SEND)
                intentshare.setType(Constant.SHARE_TYPE)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                    .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_beauty) + url)
                Intent.createChooser(intentshare, getString(R.string.share))
                startActivity(intentshare)
                return true
            }
            R.id.action_save -> {
                if (!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)!!) {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    return true
                }
                mPbLoading.visibility = View.VISIBLE
                lifecycleScope.launch {
                    //lifecycleScope使协程的生命周期随着activity的生命周期变化
                    saveImage()
                    mPbLoading.visibility = View.GONE
                    SnackbarUtil.ShortSnackbar(mIvGirl,getString(R.string.save_picture_success)).show();
                }
                return true
            }
            R.id.action_set_wallpaper -> {
                if (!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)!!) {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    return true
                }
                mPbLoading.visibility = View.VISIBLE
                lifecycleScope.launch {
                    //lifecycleScope使协程的生命周期随着activity的生命周期变化
                    setWallpaper()
                    mPbLoading.visibility = View.GONE
                    SnackbarUtil.ShortSnackbar(mIvGirl,getString(R.string.set_wallpaper_success)).show();
                }
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
        mWallpaperManager = WallpaperManager.getInstance(this);
//        girlDetailAdapter = GirlDetailAdapter(this,gankDetailList!!)
//        mVpGirls.adapter = girlDetailAdapter
    }

    fun initData (intent: Intent?) {
//        gankDetailList = intent?.getSerializableExtra(NavigationUtils.GANK_DATA_KEY) as ArrayList<GankDetailInfo>?
        gankDetailInfo = intent?.getSerializableExtra(NavigationUtils.GANK_DATA_KEY) as GankDetailInfo?
        Loger.e(TAG,"initData-gankDetailList?.size = " + gankDetailList?.size)

        ImageUtils.instance.loadImage(this,mIvGirl,gankDetailInfo?.images?.get(0))
    }


    suspend fun saveImage () {
        withContext(Dispatchers.IO) {
            val bitmap: Bitmap = mIvGirl.drawable.toBitmap() //可以传入图片的大小，默认是显示的图片

            val imgUrl = gankDetailInfo?.images?.get(0)
            val fileName = imgUrl?.substring(imgUrl.lastIndexOf("/") + 1, imgUrl?.length) + ".png"
            val dir = FileUtil.getExRootFolder()

            BitmapUtil.saveBitmap(bitmap,dir?.absolutePath,fileName!!,true)
        }
    }

    suspend fun setWallpaper () {
        val bitmap: Bitmap = mIvGirl.drawable.toBitmap() //可以传入图片的大小，默认是显示的图片
        withContext(Dispatchers.IO) {
            mWallpaperManager?.setBitmap(bitmap);
        }
    }


}