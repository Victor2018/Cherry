package com.victor.lib.coremodel.http.datasource

import android.text.TextUtils
import com.victor.lib.coremodel.data.BaseReq
import com.victor.lib.coremodel.data.HttpError
import com.victor.lib.coremodel.data.HttpResult
import com.victor.lib.coremodel.http.HttpErrorType
import com.victor.lib.coremodel.util.Loger
import org.victor.http.lib.adapter.NetworkResponse

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AbsDS
 * Author: Victor
 * Date: 2022/9/29 12:07
 * Description: 
 * -----------------------------------------------------------------
 */

open class AbsDS {
    val TAG = javaClass.simpleName

    fun handleRespone(response: NetworkResponse<Any, HttpError>): HttpResult<Any> {
        Loger.e(TAG, "handleRespone()......")
        when (response) {
            is NetworkResponse.Success -> {
                Loger.e(TAG, "handleRespone()......Success")
                try {
                    if (response.body is BaseReq<*>) {
                        val data = response.body as BaseReq<*>
                        Loger.e(TAG, "handleRespone()......code = ${data.errorCode}")
                        if (data.errorCode != 0) {
                            if (data.errorCode == -1001) {
                                Loger.e(
                                    TAG,
                                    "handleRespone()......token is null or invalid will to login"
                                )
//                                LiveDataBus.send(LoginActions.TOKEN_INVALID)
                                return HttpResult.Error(data.errorCode, data.errorMsg)
                            }
                            return HttpResult.Error(data.errorCode, data.errorMsg)
                        }

                        return HttpResult.Success(data)
                    } else {
                        return HttpResult.Success(response.body)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Loger.e(TAG, "handleRespone()......Success-ERROR")
                    return HttpResult.Error(0, e.localizedMessage)
                }
            }
            is NetworkResponse.ApiError -> {
                Loger.e(TAG, "handleRespone()......ApiError-response = ${response}")
                var message = response.body.statusMessage
                if (TextUtils.isEmpty(message)) {
                    message = response.body.statusCode
                }
                if (TextUtils.isEmpty(message)) {
                    message = "${response.code}"
                }
                return HttpResult.Error(HttpErrorType.API, message)
            }
            is NetworkResponse.NetworkError -> {
                Loger.e(TAG, "handleRespone()......NetworkError")
                var message = response.error.localizedMessage
                message = "当前没有网络,请检查您的网络后再重试"
                return HttpResult.Error(HttpErrorType.NETWORK, message)
            }
            is NetworkResponse.UnknownError -> {
                Loger.e(TAG, "handleRespone()......UnknownError = ${response.error.toString()}")
                var message = response.error?.localizedMessage
                message = "服务器开小差了,请稍后重试!"
                return HttpResult.Error(HttpErrorType.UNKNOWN, message)
            }
            is NetworkResponse.ServerError -> {
                Loger.e(TAG, "handleRespone()......ServerError")
                var code = response.code
                var error = response.error
                var message = "code = $code,error = $error"
                message = "服务器开小差了,请稍后重试!"
                return HttpResult.Error(HttpErrorType.SERVER, message)
            }
        }
    }
}