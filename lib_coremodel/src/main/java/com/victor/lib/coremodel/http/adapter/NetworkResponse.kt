package com.victor.lib.coremodel.http.adapter

import java.io.IOException


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NetworkResponse
 * Author: Victor
 * Date: 2021/2/24 14:14
 * Description: Represents success response with body
 * -----------------------------------------------------------------
 */
sealed class NetworkResponse<out T : Any, out U : Any> {

    /**
     * Represents success response with body.
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Represents failure response (non-2xx) with body.
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     *  Represents network failure (such as no internet connection).
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * Represents unexpected exceptions (for example parsing issues).
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()

    /**
     * server error (such as body == null or errorBody == null).
     */
    data class ServerError(val code: Int,val error: String) : NetworkResponse<Nothing, Nothing>()
}