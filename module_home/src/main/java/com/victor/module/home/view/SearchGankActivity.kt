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
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.JsonUtils
import com.victor.lib.coremodel.util.Loger
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.coremodel.data.*
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.vm.HomeVM
import com.victor.module.home.R
import com.victor.module.home.view.adapter.SearchFilterAdapter
import com.victor.module.home.view.adapter.SearchGankAdapter
import com.yalantis.filter.listener.FilterListener
import com.yalantis.filter.widget.Filter
import kotlinx.android.synthetic.main.activity_gank.toolbar
import kotlinx.android.synthetic.main.activity_search_gank.*
import org.victor.funny.util.ResUtils
import org.victor.funny.util.ToastUtils
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
class SearchGankActivity: BaseActivity(),SearchView.OnQueryTextListener,View.OnClickListener,
    AdapterView.OnItemClickListener, LMRecyclerView.OnLoadMoreListener,
    FilterListener<HotKeyInfo> {

    private val homeVM by viewModels<HomeVM> {
        InjectorUtils.provideHomeVMFactory(this)
    }

    var searchGankAdapter: SearchGankAdapter? = null
    var currentPage = 0
    var query: String? = null

    var mTitles: List<HotKeyInfo>? = null
    var mFilter: Filter<HotKeyInfo>? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity, type: String) {
            var intent = Intent(activity, SearchGankActivity::class.java)
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
        subscribeUi()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchGankAdapter = SearchGankAdapter(this,this)
        mRvSearchGank.setHasFixedSize(true)
        mRvSearchGank.adapter = searchGankAdapter

        mRvSearchGank.setLoadMoreListener(this)


        mFilter = filter as Filter<HotKeyInfo>?
        mFilter?.listener = this

        //the text to show when there's no selected items
        mFilter?.noSelectedItemText = ResUtils.getStringRes(com.victor.lib.common.R.string.hot_search_keywords)
    }

    fun initData () {
        sendHotKeyRequest()
    }

    fun subscribeUi() {
        homeVM.hotKeyData.observe(this, Observer {
            when (it) {
                is HttpResult.Success -> {
                    showHotKeyData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.showShort(it.message.toString())
                }
            }
        })

        homeVM.queryData.observe(this, Observer {
            when (it) {
                is HttpResult.Success -> {
                    showQueryData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.showShort(it.message.toString())
                }
            }
        })
    }

    fun sendHotKeyRequest () {
        homeVM.fetchHotKey()
    }

    fun sendQueryRequest (key: String?) {
        homeVM.fetchQuery(currentPage,key)
    }

    fun showHotKeyData (data: BaseReq<List<HotKeyInfo>>) {
        val colors = ResUtils.getIntArrayRes(com.victor.lib.common.R.array.search_filter_colors)
        mTitles = data?.data
        mFilter?.adapter = SearchFilterAdapter(this,data?.data,colors)
        mFilter?.expand()
        mFilter?.build()
    }

    fun showQueryData (data: BaseReq<ListData<HomeSquareInfo>>) {
        searchGankAdapter?.showData(data.data?.datas,null,mRvSearchGank,currentPage)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView
        val textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.setHighlightColor(getResources().getColor(com.victor.lib.common.R.color.colorAccent))
        textView.setCursorVisible(true)

        searchView.setMaxWidth(Integer.MAX_VALUE)
        searchView.onActionViewExpanded()
        searchView.setQueryHint(getString(com.victor.lib.common.R.string.search_tip))
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

                sendQueryRequest(query)
            }
        }

        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.setAccessible(true)
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(com.victor.lib.common.R.drawable.ic_search_white_24dp)
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
        sendQueryRequest(query)
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
            searchGankAdapter?.getItem(position)?.link!!)
    }

    override fun OnLoadMore() {
        currentPage++
        sendQueryRequest(query)
    }

    override fun onFilterDeselected(item: HotKeyInfo) {
        Loger.e(TAG,"onFilterDeselected()......item = ${JsonUtils.toJSONString(item)}")
    }

    override fun onFilterSelected(item: HotKeyInfo) {
        Loger.e(TAG,"onFilterSelected()......item = ${JsonUtils.toJSONString(item)}")
        mFilter?.collapse()
        if (item.getText().equals(mTitles?.get(0))) {
            mFilter?.deselectAll()
        }
    }

    override fun onFiltersSelected(filters: ArrayList<HotKeyInfo>) {
        Loger.e(TAG,"onFiltersSelected()......filters = ${JsonUtils.toJSONString(filters)}")

        var querySb = StringBuffer()
        for (item in filters) {
            querySb.append(item.name + " ")
        }
        Loger.e(TAG,"onFiltersSelected()......querySb = ${querySb}")
        ToastUtils.showDebug(querySb.toString())
        if (querySb.length <= 0) return

        query = querySb.substring(0,querySb.length-1)
        currentPage = 1
        sendQueryRequest(query)
    }

    override fun onNothingSelected() {
        Loger.e(TAG,"onNothingSelected()......")
        searchGankAdapter?.clear()
        searchGankAdapter?.notifyDataSetChanged()
    }
}