package com.victor.lib.coremodel.data


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Result
 * Author: Victor
 * Date: 2021/2/24 14:26
 * Description: 
 * -----------------------------------------------------------------
 */
sealed class HttpResult<out T : Any> {

    class Success<out T : Any>(val value: T) : HttpResult<T>()

    class Error(val code: Int,val message: String?) : HttpResult<Nothing>()
}