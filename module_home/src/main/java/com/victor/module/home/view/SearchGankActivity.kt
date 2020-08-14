package com.victor.module.home.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.coremodel.data.HotKeyInfo
import com.victor.lib.coremodel.viewmodel.SearchGankViewModel
import com.victor.lib.coremodel.viewmodel.SearchGankViewModel.SearchGankLiveDataVMFactory
import com.victor.module.home.R
import com.victor.module.home.databinding.ActivitySearchGankBinding
import com.victor.module.home.view.adapter.SearchFilterAdapter
import com.victor.module.home.view.adapter.SearchGankAdapter
import com.yalantis.filter.listener.FilterListener
import com.yalantis.filter.widget.Filter
import kotlinx.android.synthetic.main.activity_gank.toolbar
import kotlinx.android.synthetic.main.activity_search_gank.*
import org.victor.funny.util.ResUtils
import java.util.ArrayList

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchGankActivity
 * Author: Victor
 * Date: 2020/7/29 上午 11:31
 * Description:
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.SearchGankAct)
class SearchGankActivity: BaseActivity(),SearchView.OnQueryTextListener,View.OnClickListener,
    AdapterView.OnItemClickListener, LMRecyclerView.OnLoadMoreListener,
    FilterListener<HotKeyInfo> {
    private val viewmodel: SearchGankViewModel by viewModels { SearchGankLiveDataVMFactory }

    var searchGankAdapter: SearchGankAdapter? = null
    var currentPage = 1
    var query: String? = null

    var mTitles: List<HotKeyInfo>? = null
    var mFilter: Filter<HotKeyInfo>? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity, type: String) {
            var intent = Intent(activity, GankActivity::class.java)
            intent.putExtra(NavigationUtils.TYPE_KEY, type)
            activity.startActivity(intent)
        }
    }


    override fun getLayoutResource(): Int {
        return R.layout.activity_search_gank
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding = viewDataBinding as ActivitySearchGankBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel

        searchGankAdapter = SearchGankAdapter(this,this)
        searchGankAdapter?.setHeaderVisible(false)
        searchGankAdapter?.setFooterVisible(false)
        mRvSearchGank.setHasFixedSize(true)
        mRvSearchGank.adapter = searchGankAdapter

        mRvSearchGank.setLoadMoreListener(this)


        mFilter = filter as Filter<HotKeyInfo>?
        mFilter?.listener = this

        //the text to show when there's no selected items
        mFilter?.noSelectedItemText = ResUtils.getStringRes(R.string.hot_search_keywords)
    }

    fun initData () {
        Loger.e(TAG,"initData......")
        viewmodel.hotKeyData.observe(this, Observer {
            if (it == null) return@Observer
            if (it.data == null) return@Observer

            if (it?.data?.size!! > 0) {
                val colors = ResUtils.getIntArrayRes(R.array.search_filter_colors)
                mTitles = it?.data
                mFilter?.adapter = SearchFilterAdapter(this,it?.data,colors)
                mFilter?.expand()
                mFilter?.build()
            }
        })
        viewmodel.seachGankValue.observe(this, Observer {
            it.let {it1 ->
                it.data.let {it2 ->
                    if (currentPage == 1) {
                        searchGankAdapter?.clear()
                    }
                    searchGankAdapter?.setFooterVisible(it.page < it.page_count)
                    if (it.page < it.page_count) {
                        searchGankAdapter?.setLoadState(searchGankAdapter?.LOADING!!)
                    } else {
                        searchGankAdapter?.setLoadState(searchGankAdapter?.LOADING_END!!)
                    }

                    searchGankAdapter?.add(it.data)
                    searchGankAdapter?.notifyDataSetChanged()
                }
            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView
        val textView = searchView.findViewById(R.id.search_src_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.setHighlightColor(getResources().getColor(R.color.colorAccent))
        textView.setCursorVisible(true)

        searchView.setMaxWidth(Integer.MAX_VALUE)
        searchView.onActionViewExpanded()
        searchView.setQueryHint(getString(R.string.search_tip))
        searchView.setOnQueryTextListener(this)
        searchView.setSubmitButtonEnabled(true)
        searchView.setFocusable(false)
        searchView.setFocusableInTouchMode(false)

        searchView.setOnQueryTextFocusChangeListener { view, queryTextFocused ->
            if (!queryTextFocused) {
                if (TextUtils.isEmpty(searchView.query)) {
                    onBackPressed()
                    return@setOnQueryTextFocusChangeListener
                }
                searchMenuItem.collapseActionView()
                searchView.setQuery("", false)
            }
        }

        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.setAccessible(true)
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        this.query = query
        currentPage = 1
        viewmodel.searchGank(query,currentPage)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initData()
    }

    override fun onClick(v: View?) {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        WebActivity.intentStart(this,searchGankAdapter?.getItem(position)?.title!!,
            searchGankAdapter?.getItem(position)?.url!!)
    }

    override fun OnLoadMore() {
        currentPage++
        viewmodel.searchGank(query,currentPage)
    }

    override fun onFilterDeselected(item: HotKeyInfo) {
        Loger.e(TAG,"onFilterDeselected()......")
    }

    override fun onFilterSelected(item: HotKeyInfo) {
        Loger.e(TAG,"onFilterSelected()......")
        if (item.getText().equals(mTitles?.get(0))) {
            mFilter?.deselectAll();
            mFilter?.collapse();
        }
    }

    override fun onFiltersSelected(filters: ArrayList<HotKeyInfo>) {
        Loger.e(TAG,"onFiltersSelected()......")

        var querySb = StringBuffer()
        for (item in filters) {
            querySb.append(item.name + " ")
        }
        if (querySb.length <= 0) return

        query = querySb.substring(0,querySb.length-1)
        currentPage = 1
        viewmodel.searchGank(query,currentPage)
    }

    override fun onNothingSelected() {
        Loger.e(TAG,"onNothingSelected()......")
        searchGankAdapter?.clear()
        searchGankAdapter?.notifyDataSetChanged()
    }
}