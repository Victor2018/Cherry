package com.victor.lib.coremodel.http.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.WeChatRes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IWeChatDataSource
 * Author: Victor
 * Date: 2020/8/5 上午 11:47
 * Description: 
 * -----------------------------------------------------------------
 */
interface IWeChatDataSource {
    val weChatData: LiveData<WeChatRes>
    suspend fun fetchWeChat()
}