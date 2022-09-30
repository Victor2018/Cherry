package com.victor.module.wechat.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.victor.lib.common.app.App
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.util.SnackbarUtil
import com.victor.lib.coremodel.data.HttpStatus
import com.victor.lib.coremodel.util.HttpUtil
import com.victor.lib.coremodel.viewmodel.WeChatViewModel
import com.victor.lib.coremodel.viewmodel.factory.WechatViewModelFactory
import com.victor.module.wechat.R
import com.victor.module.wechat.databinding.FragmentWechatBinding
import com.victor.module.wechat.view.adapter.WeChatAdapter
import kotlinx.android.synthetic.main.fragment_wechat.*
import org.victor.funny.util.ResUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WeChatFragment
 * Author: Victor
 * Date: 2020/7/22 上午 11:00
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.WeChatFgt)
class WeChatFragment: BaseFragment(), AdapterView.OnItemClickListener, Toolbar.OnMenuItemClickListener,
    AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener {
//    private val viewmodel: WeChatViewModel by viewModels {
//        WechatLiveDataVMFactory
//    }
    private lateinit var viewmodel: WeChatViewModel
    var weChatAdapter: WeChatAdapter? = null

    companion object {
        fun newInstance(): WeChatFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): WeChatFragment {
            val fragment = WeChatFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }


    override fun getLayoutResource(): Int {
        return R.layout.fragment_wechat
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
        setHasOptionsMenu(true)
        var textView: TextView = toolbar.getChildAt(0) as TextView//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT//填充父类
        textView.setGravity(Gravity.CENTER_VERTICAL)//水平居中，CENTER，即水平也垂直，自选

        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_wechat)
        toolbar.setOnMenuItemClickListener(this)

        viewmodel = ViewModelProvider(this,
            WechatViewModelFactory(
                this
            )
        ).get(WeChatViewModel::class.java)

        appbar.addOnOffsetChangedListener(this)

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlRefresh.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color.holo_red_light)

        mSrlRefresh.setOnRefreshListener(this)

        weChatAdapter = WeChatAdapter(context!!,this)
        mRvWechat.setHasFixedSize(true)
        mRvWechat.adapter = weChatAdapter
    }

    fun initData () {
        if (!HttpUtil.isNetEnable(App.get())) {
            SnackbarUtil.ShortSnackbar(mCtlTitle,ResUtils.getStringRes(
                com.victor.lib.common.R.string.network_error),
                SnackbarUtil.ALERT
            )
            return
        }
        viewmodel.weChatData.observe(viewLifecycleOwner, Observer {
            mSrlRefresh.isRefreshing = false
            it.let {
                when (it.errorCode) {
                    HttpStatus.WAN_ANDROID_SUCCESS -> {
                        weChatAdapter?.clear()
                        weChatAdapter?.add(it.data)
                        weChatAdapter?.notifyDataSetChanged()
                    }
                    else -> {
                        weChatAdapter?.clear()
                        weChatAdapter?.notifyDataSetChanged()
                    }
                }

            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        NavigationUtils.goArticleActivity(weChatAdapter?.getItem(position)?.name!!,
            weChatAdapter?.getItem(position)?.id!!)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_share -> {
                var intentshare = Intent(Intent.ACTION_SEND);
                intentshare.setType(Constant.SHARE_TYPE)
                    .putExtra(Intent.EXTRA_SUBJECT, getString(com.victor.lib.common.R.string.share))
                    .putExtra(Intent.EXTRA_TEXT,getString(com.victor.lib.common.R.string.share_app))
                Intent.createChooser(intentshare, getString(com.victor.lib.common.R.string.share))
                startActivity(intentshare);
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            //展开状态
            mSrlRefresh.isEnabled = true
        } else if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
            //折叠状态
            mSrlRefresh.isEnabled = false
        } else {
            //中间状态
        }
    }

    override fun onRefresh() {
        mSrlRefresh.isRefreshing = true
        weChatAdapter?.clear()
        weChatAdapter?.notifyDataSetChanged()

        initData()
    }
}