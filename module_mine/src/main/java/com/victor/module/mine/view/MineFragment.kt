package com.victor.module.mine.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.CacheCleanUtils
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.util.SnackbarUtil
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.viewmodel.LocalGirlsViewModel
import com.victor.lib.coremodel.viewmodel.factory.LocalGirlsViewModelFactory
import com.victor.module.mine.R
import com.victor.module.mine.databinding.FragmentMineBinding
import com.victor.module.mine.view.adapter.FavGirlsAdapter
import com.victor.player.library.util.AppUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import org.victor.funny.util.ResUtils

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
class MineFragment: BaseFragment(),View.OnClickListener, Toolbar.OnMenuItemClickListener,
    AdapterView.OnItemClickListener {
    var fontStyle: Typeface? = null

    private lateinit var localGirlsViewModel: LocalGirlsViewModel

    var favGirlsAdapter: FavGirlsAdapter? = null
    var gridLayoutManager: GridLayoutManager? = null

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

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
        initData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialize()
    }


    fun initialize () {
        localGirlsViewModel = ViewModelProvider(this,
            LocalGirlsViewModelFactory(
                InjectorUtils.getLocalGirlsRepository(context!!), this
            )
        )
            .get(LocalGirlsViewModel::class.java)

        val binding = viewDataBinding as FragmentMineBinding

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = localGirlsViewModel

        setHasOptionsMenu(true)

        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_mine)
        toolbar.setOnMenuItemClickListener(this)

        mTvFavGirlsCount.setOnClickListener(this)
        mTvClearCache.setOnClickListener(this)

        fontStyle = Typeface.createFromAsset(context?.assets, "fonts/zuo_an_lian_ren.ttf")

        gridLayoutManager = GridLayoutManager(activity,2,GridLayoutManager.HORIZONTAL,false)
        gridLayoutManager?.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 2
                } else {
                    return 1
                }

            }
        })
        mRvFavGirls.layoutManager = gridLayoutManager

        favGirlsAdapter = FavGirlsAdapter(activity!!,this)
        favGirlsAdapter?.setHeaderVisible(false)
        favGirlsAdapter?.setFooterVisible(false)
        mRvFavGirls.setHasFixedSize(true)
        mRvFavGirls.adapter = favGirlsAdapter
    }

    fun initData () {
        localGirlsViewModel.favGirls.observe(this, androidx.lifecycle.Observer {
            mTvFavGirlsCount.text = it.size.toString()
            favGirlsAdapter?.clear()
            favGirlsAdapter?.add(it)
            favGirlsAdapter?.notifyDataSetChanged()
        })

        mTvVersion.text = String.format("v%s", AppUtil.getAppVersionName(activity!!))

        try {
            var text: String? = CacheCleanUtils.getTotalCacheSize(activity!!)
            mTvClearCache.setText(text)
        } catch (e: Exception) {
            e.printStackTrace()
            mTvClearCache.setText("0KB")
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val nickName = sharedPref.getString(ResUtils.getStringRes(R.string.nickname),
            ResUtils.getStringRes(R.string.default_nickname))
        val signature = sharedPref.getString(ResUtils.getStringRes(R.string.signature),
            ResUtils.getStringRes(R.string.default_signature))

        mTvNickName.text = nickName
        mTvSignature.text = signature

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mTvFavGirlsCount -> {
                toGirlsDetail(0)
            }
            R.id.mTvClearCache -> {
                CacheCleanUtils.clearAllCache(activity!!)
                mTvClearCache.setText("0KB")
                SnackbarUtil.ShortSnackbar(mTvClearCache,getString(R.string.cache_cleared)).show()
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_edit -> {
                EditUserActivity.intentStart(activity!! as AppCompatActivity)
                return true
            }
            R.id.action_setting -> {
                SettingActivity.intentStart(activity!! as AppCompatActivity)
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        toGirlsDetail(position)
    }

    fun toGirlsDetail (position: Int) {
        var datas = ArrayList<GankDetailInfo>()

        favGirlsAdapter?.getDatas()?.map {datas.add(it.plant) }

        NavigationUtils.goGirlsDetailActivity(position, datas)
    }

}

