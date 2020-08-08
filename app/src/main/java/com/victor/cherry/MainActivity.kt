package com.victor.cherry

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.victor.cherry.adapter.HomeFragmentAdapter
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.MainHandler
import com.victor.lib.common.view.widget.bottombar.ReadableBottomBar
import kotlinx.android.synthetic.main.activity_main.*
import org.victor.funny.util.ResUtils
import java.util.*

@Route(path = ARouterPath.MainAct)
class MainActivity : BaseActivity(),View.OnClickListener, ReadableBottomBar.ItemSelectListener {

    var homeFragmentAdapter: HomeFragmentAdapter? = null
    var fragmentList: MutableList<BaseFragment> = ArrayList();
    var navTitles: Array<String> ? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        statusBarTextColorBlack = true
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
//        setSupportActionBar(toolbar)

        homeFragmentAdapter = HomeFragmentAdapter(supportFragmentManager)

        val homeFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.HomeFgt).navigation() as BaseFragment
        val girlsFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.GirlsFgt).navigation() as BaseFragment
        val weChatFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.WeChatFgt).navigation() as BaseFragment
        val tvFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.TvFgt).navigation() as BaseFragment
        val mineFrag: BaseFragment = ARouter.getInstance().build(ARouterPath.MineFgt).navigation() as BaseFragment
        fragmentList.add(homeFrag)
        fragmentList.add(girlsFrag)
        fragmentList.add(weChatFrag)
        fragmentList.add(tvFrag)
        fragmentList.add(mineFrag)

        homeFragmentAdapter?.fragmetList = fragmentList
        mVpHome.adapter = homeFragmentAdapter

        mNavBar.setOnItemSelectListener(this)

      /*  var textView: TextView  = toolbar.getChildAt(0) as TextView;//主标题
        textView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;//填充父类
        textView.setGravity(Gravity.CENTER_HORIZONTAL);//水平居中，CENTER，即水平也垂直，自选*/

    }

    fun initData () {
        navTitles = ResUtils.getStringArrayRes(R.array.nav_title)
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
//        toolbar.title = navTitles?.get(index)
        if (index < fragmentList.size) {
            mVpHome.currentItem = index
        }
    }

    fun showNavBar () {
        val layoutParams = mNavBar.getLayoutParams() as LinearLayout.LayoutParams
        layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
        layoutParams.height = resources.getDimension(R.dimen.dp_100).toInt()
        mNavBar.layoutParams = layoutParams

//        mNavBar.startAnimation(AnimUtil.bottomEnter())
    }
    fun hideNavBar () {
//        mNavBar.startAnimation(AnimUtil.bottomExit())

        val layoutParams = mNavBar.getLayoutParams() as LinearLayout.LayoutParams
        layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
        layoutParams.height = resources.getDimension(R.dimen.dp_0).toInt()
        mNavBar.layoutParams = layoutParams
    }

    override fun update(observable: Observable?, data: Any?) {
        super.update(observable, data)
        if (data is Int) {
            when (data) {
                Constant.Action.SHOW_NAV_BAR -> {
                    MainHandler.get().runMainThread(Runnable {
                        showNavBar()
                    })
                }
                Constant.Action.HIDE_NAV_BAR -> {
                    MainHandler.get().runMainThread(Runnable {
                        hideNavBar()
                    })
                }
            }
        }
    }

    override fun onBackPressed() {
        if (fragmentList.get(mVpHome.currentItem).handleBackEvent()) {
            return
        }
        super.onBackPressed()
    }
}