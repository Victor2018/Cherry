package com.victor.lib.coremodel.http.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NetworkResponseAdapterFactory
 * Author: Victor
 * Date: 2021/2/24 14:16
 * Description: implementation that returns [NetworkResponseAdapter] for the interface
 * methods which it can handle or null otherwise
 * -----------------------------------------------------------------
 */
class NetworkResponseAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // Suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // Check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        // Get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not NetworkResponse then we can't handle it, so we return null
        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        // The response type is NetworkResponse and should be parameterized
        check(responseType is ParameterizedType) {
            "response type must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>"
        }

        // Extract the success and error types from the NetworkResponse parameterized type
        val successBodyType = getParameterUpperBound(0, responseType)
        val errorBodyType = getParameterUpperBound(1, responseType)

        // Get the error converter
        val errorBodyConverter = retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return NetworkResponseAdapter<Any, Any>(successBodyType, errorBodyConverter)
    }
}