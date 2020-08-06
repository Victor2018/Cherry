package com.victor.module.wechat.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.coremodel.viewmodel.WeChatViewModel
import com.victor.lib.coremodel.viewmodel.WeChatViewModel.WechatLiveDataVMFactory
import com.victor.module.wechat.R
import com.victor.module.wechat.databinding.FragmentWechatBinding
import com.victor.module.wechat.view.adapter.WeChatAdapter
import kotlinx.android.synthetic.main.fragment_wechat.*

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
class WeChatFragment: BaseFragment(), AdapterView.OnItemClickListener, Toolbar.OnMenuItemClickListener {
    private val viewmodel: WeChatViewModel by viewModels { WechatLiveDataVMFactory }
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

        val binding = viewDataBinding as FragmentWechatBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel

        weChatAdapter = WeChatAdapter(context!!,this)
        mRvWechat.setHasFixedSize(true)
        mRvWechat.adapter = weChatAdapter
    }

    fun initData () {
        viewmodel.weChatData.observe(viewLifecycleOwner, Observer {
            it.let {
                weChatAdapter?.clear()
                weChatAdapter?.add(it.data)
                weChatAdapter?.notifyDataSetChanged()
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
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                    .putExtra(Intent.EXTRA_TEXT,getString(R.string.share_app));
                Intent.createChooser(intentshare, getString(R.string.share));
                startActivity(intentshare);
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }
}