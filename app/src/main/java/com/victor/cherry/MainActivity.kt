package com.victor.cherry

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.NavigationUtils
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = ARouterPath.MainAct)
class MainActivity : BaseActivity(),View.OnClickListener {
    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBtnHome.setOnClickListener(this)
        mBtnMine.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mBtnHome -> {
                NavigationUtils.goHomeActivity()
            }
            R.id.mBtnMine -> {
                NavigationUtils.goMineActivity()
            }
        }
    }
}