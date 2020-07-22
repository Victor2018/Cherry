package com.victor.module.wechat.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.GankRes
import com.victor.lib.coremodel.entity.WeChatRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IWeChatDataSource
 * Author: Victor
 * Date: 2020/7/22 上午 10:49
 * Description: 
 * -----------------------------------------------------------------
 */
interface IWeChatDataSource {
    fun fetchWeChatData(): LiveData<WeChatRes>
}