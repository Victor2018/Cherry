package com.victor.lib.coremodel.http.datasource

import android.util.Log
import com.victor.lib.coremodel.data.HttpError
import com.victor.lib.coremodel.data.HttpResult
import com.victor.lib.coremodel.http.adapter.NetworkResponse


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseDataSource
 * Author: Victor
 * Date: 2021/2/24 14:24
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseDataSource {
    val TAG = javaClass.simpleName

    fun handleRespone (response: NetworkResponse<Any, HttpError>): HttpResult<Any> {
        Log.e(TAG,"handleRespone()......")
        when (response) {
            is NetworkResponse.Success -> {
                Log.e(TAG,"handleRespone()......Success")
                try {
                    return HttpResult.Success(response.body)
                }  catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG,"handleRespone()......Success-ERROR")
                    return HttpResult.Error(null,e.localizedMessage)
                }
            }
            is NetworkResponse.ApiError -> {
                Log.e(TAG,"handleRespone()......ApiError")
                val message = response.body.statusMessage
                return HttpResult.Error(null,message)
            }
            is NetworkResponse.NetworkError -> {
                Log.e(TAG,"handleRespone()......NetworkError")
                val message = response.error.localizedMessage
                return HttpResult.Error(null,message)
            }
            is NetworkResponse.UnknownError -> {
                Log.e(TAG,"handleRespone()......UnknownError")
                val message = response.error?.localizedMessage
                return HttpResult.Error(null,message)
            }
            is NetworkResponse.ServerError -> {
                Log.e(TAG,"handleRespone()......ServerError")
                var code = response.code
                var error = response.error
                val message = "code = $code,error = $error"
                return HttpResult.Error(null,message)
            }
        }
    }
}