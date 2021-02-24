package com.victor.lib.coremodel.http.adapter

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NetworkResponseCall
 * Author: Victor
 * Date: 2021/2/24 14:15
 * Description: interface implementation in order to make Retrofit return [NetworkResponse]
 * when API call is triggered
 * -----------------------------------------------------------------
 */
internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()
                if (response.isSuccessful) {
                    if (body != null) {
                        // Response is successful
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ServerError(code,"body == null"))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        // Response is not successful
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiError(errorBody, code))
                        )
                    } else {
                        // Response is not successful but the error body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ServerError(code,"errorBody == null"))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable)
                    else -> NetworkResponse.UnknownError(throwable)
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()
}