package com.victor.module.home.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.cherry.viewmodel.HomeViewModel
import com.victor.cherry.viewmodel.LiveDataVMFactory
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.MainHandler
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.view.widget.KeywordsFlow
import com.victor.lib.coremodel.entity.GankDetailInfo
import com.victor.lib.coremodel.entity.HotKeyInfo
import com.victor.lib.coremodel.entity.RepositoryType
import com.victor.lib.coremodel.http.locator.ServiceLocator
import com.victor.lib.coremodel.viewmodel.SearchGankViewModel
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeBinding
import com.victor.module.home.view.adapter.GankAdapter
import com.victor.module.home.view.adapter.GankLoadStateAdapter
import kotlinx.android.synthetic.main.activity_gank.toolbar
import kotlinx.android.synthetic.main.activity_search_gank.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.victor.funny.util.ToastUtils
import java.util.*
import kotlin.collections.ArrayList

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
class SearchGankActivity: BaseActivity(),SearchView.OnQueryTextListener,View.OnClickListener {
    private val viewmodel: HomeViewModel by viewModels { LiveDataVMFactory }
    var viewDataBinding : ViewDataBinding? = null;

    private lateinit var adapter: GankAdapter
    var key: String? = "android"

    private val searchViewmodel: SearchGankViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchGankViewModel(key!!, ServiceLocator.instance().getRepository(RepositoryType.SEARCH_GANK)) as T
            }
        }
    }

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

        val binding = viewDataBinding as FragmentHomeBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = viewmodel

        initAdapter()
        initSwipeToRefresh()

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlSearch.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
            android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private fun feedKeywordsFlow(
        keywordsFlow: KeywordsFlow,
        arr: List<HotKeyInfo>
    ) {
        val random = Random()
        for (i in 0 until KeywordsFlow.MAX) {
            val ran: Int = random.nextInt(arr.size)
            val tmp = arr[ran]
            keywordsFlow.feedKeyword(tmp.name)
        }
    }

    fun initData () {
        viewmodel.hotKeyData.observe(this, Observer {
            it.data?.let { it1 ->
            }
        })
    }

    private fun initAdapter() {
        adapter = GankAdapter()
        mRvSearchGank.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GankLoadStateAdapter(
                adapter
            ),
            footer = GankLoadStateAdapter(
                adapter
            )
        )

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                mSrlSearch.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            searchViewmodel.datas.collectLatest {
                adapter.submitData(it as PagingData<GankDetailInfo>)
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
            adapter.dataRefreshFlow.collectLatest {
                mRvSearchGank.scrollToPosition(0)
            }
        }
    }

    private fun initSwipeToRefresh() {
        mSrlSearch.setOnRefreshListener { adapter.refresh() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search);
        val searchView = searchMenuItem?.actionView as SearchView
        val textView = searchView.findViewById(R.id.search_src_text) as TextView;
        textView.setTextColor(Color.WHITE);
        textView.setHighlightColor(getResources().getColor(R.color.colorAccent));
        textView.setCursorVisible(true);

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
}