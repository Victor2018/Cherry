package com.victor.cherry

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.victor.cherry.adapter.HomeFragmentAdapter
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.view.widget.bottombar.ReadableBottomBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Route(path = ARouterPath.MainAct)
class MainActivity : BaseActivity(),View.OnClickListener, ReadableBottomBar.ItemSelectListener {

    var homeFragmentAdapter: HomeFragmentAdapter? = null
    var fragmentList: MutableList<BaseFragment> = ArrayList();

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarTextColorBlack = true
        super.onCreate(savedInstanceState)

        homeFragmentAdapter = HomeFragmentAdapter(supportFragmentManager)

        val homeFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.HomeFgt).navigation() as BaseFragment
        val girlsFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.GirlsFgt).navigation() as BaseFragment
        val mineFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.MineFgt).navigation() as BaseFragment
        fragmentList.add(homeFrag)
        fragmentList.add(girlsFrag)
        fragmentList.add(mineFrag)

        homeFragmentAdapter?.fragmetList = fragmentList
        mVpHome.adapter = homeFragmentAdapter

        mBottomBar.setOnItemSelectListener(this)
    }

    override fun onResume() {
        super.onResume()
        particleView.resume()
    }

    override fun onPause() {
        super.onPause()
        particleView.pause()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
        }
    }

    override fun onItemSelected(index: Int) {
        if (index < fragmentList.size) {
            mVpHome.currentItem = index
        }
    }
}