package com.victor.module.tv.view

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Message
import android.view.*
import android.widget.AdapterView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.util.MainHandler
import com.victor.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.module.DataObservable
import com.victor.lib.common.util.*
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.util.Loger
import com.victor.lib.coremodel.viewmodel.TvViewModel
import com.victor.module.tv.R
import com.victor.module.tv.databinding.FragmentTvBinding
import com.victor.module.tv.view.adapter.TvAdapter
import com.victor.player.library.module.Player
import kotlinx.android.synthetic.main.fragment_tv.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvFragment
 * Author: Victor
 * Date: 2020/7/24 上午 11:02
 * Description: 
 * -----------------------------------------------------------------
 */
@Route(path = ARouterPath.TvFgt)
class TvFragment: BaseFragment(), AdapterView.OnItemClickListener, MainHandler.OnMainHandlerImpl,
    View.OnClickListener {

    private val tvViewModel: TvViewModel by viewModels {
        InjectorUtils.provideTvViewModelFactory(this)
    }

    var tvAdapter: TvAdapter? = null

    var mPlayer: Player? = null
    var isFullScreenPlay: Boolean = false

    companion object {
        fun newInstance(): TvFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TvFragment {
            val fragment = TvFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_tv
    }

    override fun handleBackEvent(): Boolean {
        if (isFullScreenPlay) {
            exitFullScreen()
            return true
        }
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
        MainHandler.get().register(this)

//        setHasOptionsMenu(true)
//        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        val binding = viewDataBinding as FragmentTvBinding?

        // Set the LifecycleOwner to be able to observe LiveData objects
        binding?.lifecycleOwner = this

        // Bind ViewModel
        binding?.viewmodel = tvViewModel

        tvAdapter = TvAdapter(requireContext(),this)
        mRvChannels.setHasFixedSize(true)
        mRvChannels.adapter = tvAdapter

        mFabFullScreen.setOnClickListener(this)

        mPlayer = Player(mTvPlay,MainHandler.get())

    }

    fun initData () {
        tvViewModel.categories.observe(viewLifecycleOwner, Observer {
            tvAdapter?.clear()
            tvAdapter?.add(it)
            tvAdapter?.notifyDataSetChanged()

            mCtlTvTitle.title = tvAdapter?.getItem(0)?.categoryChannels?.get(0)?.channel_name
            mPlayer?.playUrl(tvAdapter?.getItem(0)?.categoryChannels?.
            get(0)?.play_urls?.get(0)?.play_url,false)

        })

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mCtlTvTitle.title = tvAdapter?.getItem(id.toInt())?.categoryChannels?.get(position)?.channel_name
        mPlayer?.playUrl(tvAdapter?.getItem(id.toInt())?.categoryChannels?.
        get(position)?.play_urls?.get(0)?.play_url,false)
    }

    override fun handleMainMessage(message: Message?) {
        when (message?.what) {
            Player.PLAYER_PREPARING -> {
            }
            Player.PLAYER_PREPARED -> {
                mTvPlay.startAnimation(AnimUtil.topEnter())
                mIvTvPoster.startAnimation(AnimUtil.bottomExit())
                mIvTvPoster.visibility = View.INVISIBLE
            }
            Player.PLAYER_ERROR -> {
            }
            Player.PLAYER_BUFFERING_START -> {
            }
            Player.PLAYER_BUFFERING_END -> {
            }
            Player.PLAYER_PROGRESS_INFO -> {
            }
            Player.PLAYER_COMPLETE -> {
            }
        }
    }
    fun fullScreen () {
        Loger.e(TAG,"fullScreen()......")
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        StatusBarUtil.hideStatusBar(requireContext())
        val layoutParams = appbar.getLayoutParams() as CoordinatorLayout.LayoutParams
        layoutParams.height = CoordinatorLayout.LayoutParams.MATCH_PARENT
        layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
        appbar.layoutParams = layoutParams

        mIvTvPoster.visibility = View.INVISIBLE

        isFullScreenPlay = !isFullScreenPlay

        setFabBtnVisible(false)

        DataObservable.instance.setData(Constant.Action.HIDE_NAV_BAR)
    }

    fun exitFullScreen () {
        Loger.e(TAG,"exitFullScreen()......")
        val mConfiguration = this.resources.configuration //获取设置的配置信息
        val ori = mConfiguration.orientation //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//强制为竖屏
            StatusBarUtil.showStatusBar(requireContext())

            val layoutParams = appbar.getLayoutParams() as CoordinatorLayout.LayoutParams
            layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
            layoutParams.height = resources.getDimension(com.victor.screen.match.library.R.dimen.dp_456).toInt()
            appbar.layoutParams = layoutParams

            removePlayViewFormParent()
            mIvTvPoster.visibility = View.VISIBLE
            mCtlTvTitle.addView(mTvPlay, 0)
        }
        isFullScreenPlay = !isFullScreenPlay

        setFabBtnVisible(true)

        DataObservable.instance.setData(Constant.Action.SHOW_NAV_BAR)
    }

    private fun removePlayViewFormParent() {
        val parent = mTvPlay.getParent()
        if (parent != null && parent is ViewGroup) {
            parent.removeView(mTvPlay)
        }
    }


    @SuppressLint("RestrictedApi")
    fun setFabBtnVisible (show: Boolean) {
        if (show) {
            mFabFullScreen.setImageResource(R.mipmap.ic_fullscreen)
            mFabFullScreen.visibility = View.VISIBLE
        } else {
            mFabFullScreen.setImageResource(R.mipmap.ic_exit_fullscreen)
            mFabFullScreen.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabFullScreen -> {
                if (isFullScreenPlay) {
                    exitFullScreen()
                } else {
                    fullScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPlayer?.resume()
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainHandler.get().unregister(this)
        mPlayer?.stop()
        mPlayer = null
    }

}