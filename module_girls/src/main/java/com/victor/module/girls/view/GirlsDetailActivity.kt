package com.victor.module.girls.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.*
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.viewmodel.LocalGirlsViewModel
import com.victor.module.girls.R
import com.victor.module.girls.databinding.ActivityGirlsDetailBinding
import com.victor.module.girls.view.adapter.GirlDetailAdapter
import com.victor.module.girls.view.adapter.GirlsViewAdapter
import kotlinx.android.synthetic.main.activity_girls_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


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
class GirlsDetailActivity: BaseActivity(),AdapterView.OnItemClickListener,View.OnClickListener,
    ViewPager.OnPageChangeListener{
    var girlDetailAdapter: GirlDetailAdapter? = null
    var gankDetailList = ArrayList<GankDetailInfo>()
    var position: Int = 0
    var isFavoried: Boolean = false

    var mWallpaperManager: WallpaperManager? = null

    private val localGirlsViewModel: LocalGirlsViewModel by viewModels {
        InjectorUtils.provideLocalGirlsViewModelFactory(this)
    }

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
        fun  intentStart (activity: AppCompatActivity,position: Int,
                          sharedElement: View?,sharedElementName: String) {

            var intent = Intent(activity, GirlsDetailActivity::class.java)
            intent.putExtra(NavigationUtils.POSITION_KEY, position)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,sharedElement!!, sharedElementName)
            ActivityCompat.startActivity(activity!!, intent, options.toBundle())
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_girls_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs(intent)
        initialize()
        initLocalGirlsData()
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
                val url: String = gankDetailList?.get(mVpGirls.currentItem)?.images?.get(0)!!
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
                downImage()
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
                    SnackbarUtil.ShortSnackbar(mVpGirls,getString(R.string.set_wallpaper_success)).show();
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initArgs(intent)
        initialize()
        initLocalGirlsData()
    }

    fun initialize () {
        val binding = viewDataBinding as ActivityGirlsDetailBinding

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = localGirlsViewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mWallpaperManager = WallpaperManager.getInstance(this);

        girlDetailAdapter = GirlDetailAdapter(this,gankDetailList!!)
        mVpGirls.adapter = girlDetailAdapter
        mVpGirls.addOnPageChangeListener(this)

        mFabFavGirl.setOnClickListener(this)
    }

    fun initArgs (intent: Intent?) {
        position = intent?.getIntExtra(NavigationUtils.POSITION_KEY,0)!!

        if (intent?.getSerializableExtra(NavigationUtils.GANK_DATA_KEY) != null) {
            gankDetailList = intent?.getSerializableExtra(NavigationUtils.GANK_DATA_KEY) as ArrayList<GankDetailInfo>
        }
    }
    fun initLocalGirlsData () {
        if (gankDetailList != null && gankDetailList?.size!! > 0) {
            mVpGirls.currentItem = position!!
        } else {
            localGirlsViewModel.girls.observe(this, androidx.lifecycle.Observer {
                gankDetailList?.clear()
                gankDetailList?.addAll(it)
                girlDetailAdapter?.notifyDataSetChanged()
                mVpGirls.currentItem = position!!
            })
        }
    }

    fun downImage () {
        mPbLoading.visibility = View.VISIBLE
        lifecycleScope.launch {
            //lifecycleScope使协程的生命周期随着activity的生命周期变化
            saveImage()
            mPbLoading.visibility = View.GONE
            SnackbarUtil.ShortSnackbar(mVpGirls,getString(R.string.save_picture_success)).show();
        }
    }

    suspend fun saveImage () {
        withContext(Dispatchers.IO) {
            val bitmap: Bitmap = girlDetailAdapter?.getCurrentView()!! //可以传入图片的大小，默认是显示的图片

            val imgUrl = gankDetailList?.get(mVpGirls.currentItem)?.images?.get(0)
            val fileName = imgUrl?.substring(imgUrl.lastIndexOf("/") + 1, imgUrl?.length) + ".png"
            val dir = FileUtil.getExRootFolder()

            BitmapUtil.saveBitmap(bitmap,dir?.absolutePath,fileName!!,true)
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun setWallpaper () {
        val bitmap: Bitmap = girlDetailAdapter?.getCurrentView()!!
        withContext(Dispatchers.IO) {
            mWallpaperManager?.setBitmap(bitmap)
        }
    }

    fun setFavoriteStatus (position: Int) {
        localGirlsViewModel.isGankFavoreted(gankDetailList?.get(position)?._id!!).observe(this,
            androidx.lifecycle.Observer {
                if (it) {
                    mFabFavGirl.setImageResource(R.drawable.ic_favorite)
                    isFavoried = true
                } else {
                    mFabFavGirl.setImageResource(R.drawable.ic_unfavorite)
                    isFavoried = false
                }
            })
    }

    override fun onPermissionGranted(permissionName: Array<out String>) {
        super.onPermissionGranted(permissionName)
        Loger.e(TAG, "onPermissionGranted-Permission(s) " + Arrays.toString(permissionName) + " Granted")
        if (permissionName.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            downImage()
        }
    }

    override fun onPermissionDeclined(permissionName: Array<out String>) {
        super.onPermissionDeclined(permissionName)
        Loger.e(TAG, "onPermissionDeclined-Permission(s) " + Arrays.toString(permissionName) + " Declined")
        if (permissionName.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            SnackbarUtil.ShortSnackbar(mVpGirls,getString(R.string.save_pic_faild)).show();
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mFabFavGirl -> {
                if (isFavoried) {
                    localGirlsViewModel.removeFavGank(gankDetailList?.get(position)!!)
                    mFabFavGirl.setImageResource(R.drawable.ic_unfavorite)
                    isFavoried = false
                } else {
                    localGirlsViewModel.addFavGank(gankDetailList?.get(position)!!)
                    mFabFavGirl.setImageResource(R.drawable.ic_favorite)
                    isFavoried = true
                }
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        Loger.e(TAG,"onPageSelected-gankDetailList = " + gankDetailList)
        Loger.e(TAG,"onPageSelected-gankDetailList-size = " + gankDetailList?.size)
        this.position = position
        setFavoriteStatus(position)
    }
}