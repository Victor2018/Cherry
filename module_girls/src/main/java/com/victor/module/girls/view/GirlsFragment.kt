package com.victor.module.girls.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.module.girls.R
import com.victor.module.girls.view.adapter.GankGirlAdapter
import com.victor.module.girls.viewmodel.GirlsViewModel
import com.victor.module.girls.viewmodel.LiveDataVMFactory
import kotlinx.android.synthetic.main.fragment_girls.*
import kotlinx.android.synthetic.main.rv_girls_cell.view.*
import org.victor.funny.util.ResUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GirlsFragment
 * Author: Victor
 * Date: 2020/7/15 下午 04:16
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.GirlsFgt)
class GirlsFragment: BaseFragment(),AdapterView.OnItemClickListener,Toolbar.OnMenuItemClickListener,
    LMRecyclerView.OnLoadMoreListener{
    private val viewmodel: GirlsViewModel by viewModels { LiveDataVMFactory }

    private lateinit var gankGirlAdapter: GankGirlAdapter

    var currentPage = 1

    companion object {
        fun newInstance(): GirlsFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): GirlsFragment {
            val fragment = GirlsFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_girls
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialize()
        initData()
    }

    fun initialize () {
        setHasOptionsMenu(true)
        var textView: TextView = toolbar.getChildAt(0) as TextView//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT//填充父类
        textView.setGravity(Gravity.CENTER_VERTICAL)//水平居中，CENTER，即水平也垂直，自选

        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_girls)
        toolbar.setOnMenuItemClickListener(this)

        gankGirlAdapter = GankGirlAdapter(activity!!,this)
        mRvGirls.setHasFixedSize(true)
        mRvGirls.adapter = gankGirlAdapter
        mRvGirls.setLoadMoreListener(this)
    }

    fun initData () {
        viewmodel.fetchGirls(currentPage)
        viewmodel.girlsDataValue.observe(viewLifecycleOwner, Observer {
            it.let {it1 ->
                it.data.let {it2 ->
                    if (currentPage == 1) {
                        gankGirlAdapter?.clear()
                    }
                    gankGirlAdapter?.setFooterVisible(it.page < it.page_count)
                    if (it.page < it.page_count) {
                        gankGirlAdapter?.setLoadState(gankGirlAdapter?.LOADING!!)
                    } else {
                        gankGirlAdapter?.setLoadState(gankGirlAdapter?.LOADING_END!!)
                    }

                    gankGirlAdapter?.add(it.data)
                    gankGirlAdapter?.notifyDataSetChanged()
                }
            }

        })
    }

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        GirlsDetailActivity.intentStart(activity!! as AppCompatActivity,
            gankGirlAdapter.getDatas() as ArrayList<GankDetailInfo>,position,
        view?.iv_img,ResUtils.getStringRes(R.string.transition_girl_img))
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

    override fun OnLoadMore() {
        currentPage++
        viewmodel.fetchGirls(currentPage)
    }
}