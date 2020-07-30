package com.victor.lib.common.util

import android.os.Handler
import android.os.Looper
import android.os.Message


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MainHandler
 * Author: Victor
 * Date: 2020/7/29 下午 02:29
 * Description: 主线程Handler
 * -----------------------------------------------------------------
 */
class MainHandler: Handler(Looper.getMainLooper()) {
    companion object {
        private var sMainHandler: MainHandler? = null

        fun get(): MainHandler {
            if (sMainHandler == null) {
                sMainHandler = MainHandler()
            }
            return sMainHandler!!
        }
    }

    private val mainHandlers: LinkedHashSet<OnMainHandlerImpl> = LinkedHashSet()

    override fun handleMessage(message: Message?) {
        super.handleMessage(message)
        for (onMainHandlerImpl in mainHandlers) {
            onMainHandlerImpl?.handleMainMessage(message)
        }
    }

    fun register(onMainHandlerImpl: OnMainHandlerImpl?): MainHandler? {
        if (onMainHandlerImpl != null) {
            mainHandlers.add(onMainHandlerImpl)
        }
        return this
    }

    fun unregister(onMainHandlerImpl: OnMainHandlerImpl?) {
        if (onMainHandlerImpl != null) {
            mainHandlers.remove(onMainHandlerImpl)
        }
    }

    fun clear() {
        mainHandlers.clear()
        if (sMainHandler != null) {
            sMainHandler!!.removeCallbacksAndMessages(null)
        }
    }

    /**
     * 主线程执行
     *
     * @param runnable
     */
    fun runMainThread(runnable: Runnable?) {
        get().post(runnable)
    }

    interface OnMainHandlerImpl {
        fun handleMainMessage(message: Message?)
    }
}