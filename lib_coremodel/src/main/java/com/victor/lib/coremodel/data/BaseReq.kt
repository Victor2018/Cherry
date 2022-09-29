package com.victor.lib.coremodel.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseReq
 * Author: Victor
 * Date: 2022/9/29 11:58
 * Description: 
 * -----------------------------------------------------------------
 */

open class BaseReq<T> {
    var errorCode: Int = 0//0 代表执行成功，不建议依赖任何非0的 errorCode, -1001 代表登录失效，需要重新登录
    var errorMsg: String? = null//请求失败的错误信息
    var data: T? = null
}