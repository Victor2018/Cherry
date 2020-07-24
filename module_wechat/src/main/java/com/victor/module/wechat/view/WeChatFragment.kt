package com.victor.module.wechat.view

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.NavigationUtils
import com.victor.module.wechat.R
import com.victor.module.wechat.databinding.FragmentWechatBinding
import com.victor.module.wechat.view.adapter.WeChatAdapter
import com.victor.module.wechat.viewmodel.WeChatViewModel
import com.victor.module.wechat.viewmodel.WeChatViewModel.LiveDataVMFactory
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
class WeChatFragment: BaseFragment(), AdapterView.OnItemClickListener {
    private val viewmodel: WeChatViewModel by viewModels { LiveDataVMFactory }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        var textView: TextView = toolbar.getChildAt(0) as TextView;//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;//填充父类
        textView.setGravity(Gravity.CENTER_HORIZONTAL);//水平居中，CENTER，即水平也垂直，自选

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
            weChatAdapter?.clear()
            weChatAdapter?.add(it.data)
            weChatAdapter?.notifyDataSetChanged()
        })
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        NavigationUtils.goArticleActivity(weChatAdapter?.getItem(position)?.name!!,
            weChatAdapter?.getItem(position)?.id!!)
    }
}