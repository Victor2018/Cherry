package com.victor.lib.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.victor.lib.common.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseFragment
 * Author: Victor
 * Date: 2020/7/3 下午 06:15
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseFragment : Fragment() {
    companion object {
        val ID_KEY = "ID_KEY"
        val TAG = javaClass.simpleName
        var fragmentId = -1
    }

    protected var rootView: View? = null
    //Fragment对用户可见的标记
    var isVisibleToUser: Boolean = false
    var viewDataBinding : ViewDataBinding? = null;

    protected abstract fun getLayoutResource(): Int
    abstract fun handleBackEvent(): Boolean
    abstract fun freshFragData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            this.isVisibleToUser = true
            lazyLoad()
        } else {
            this.isVisibleToUser = false
        }
    }

    fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isVisibleToUser) {
            freshFragData()
            //数据加载完毕,恢复标记,防止重复加载
            isVisibleToUser = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewDataBinding == null) {
            viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(),container, false)
        }
        if (rootView == null) {
            if (viewDataBinding == null) {
                rootView = inflater.inflate(getLayoutResource(), container, false)
            } else {
                rootView = viewDataBinding!!.root
            }
        }

        if (rootView!!.getParent() != null) {
            val parent = rootView!!.getParent() as ViewGroup
            parent!!.removeView(rootView)
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}