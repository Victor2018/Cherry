package com.victor.lib.coremodel.http

import com.alibaba.fastjson.JSON
import okhttp3.*
import okio.BufferedSource
import okio.Okio
import okio.buffer
import okio.source
import java.io.ByteArrayInputStream


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SessionInterceptor
 * Author: Victor
 * Date: 2022/9/29 17:46
 * Description: 
 * -----------------------------------------------------------------
 */

class SessionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        return Response.Builder()
            .body(newResponseBody(response))
            .headers(response.headers)
            .message(response.message)
            .code(response.code)
            .request(response.request)
            .protocol(response.protocol)
            .build()
    }

    private fun newResponseBody(response: Response): ResponseBody? {
        return object : ResponseBody() {
            override fun contentType(): MediaType? {
                return response.body!!.contentType()
            }

            override fun contentLength(): Long {
                return response.body!!.contentLength()
            }

            override fun source(): BufferedSource {
                val result = response.body?.string() ?: ""
                if (result.endsWith("html")) {
                    /**
                     * 这里改变返回的数据，如果服务器返回的是一个HTML网页，
                     * 那么移动端也能拿到一个Json数据，用于保证数据可解析不至于崩溃
                     */
                    val tInputStringStream =
                        ByteArrayInputStream("{code:500,success:false}".toByteArray())
                    return tInputStringStream.source().buffer()
                } else {
                    val tInputStringStream =
                        ByteArrayInputStream(result.toByteArray())
                    return tInputStringStream.source().buffer()
                }
            }
        }
    }
}