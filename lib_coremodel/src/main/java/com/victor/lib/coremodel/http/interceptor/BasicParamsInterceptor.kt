package com.victor.lib.coremodel.http.interceptor

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSON
import okhttp3.*
import okio.Buffer
import java.io.IOException


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BasicParamsInterceptor
 * Author: Victor
 * Date: 2021/2/24 14:20
 * Description: 添加公共参数拦截器
 * -----------------------------------------------------------------
 */
class BasicParamsInterceptor: Interceptor {
    val TAG = "BasicParamsInterceptor"
    var queryParamsMap: MutableMap<String, String> = HashMap()
    var paramsMap: MutableMap<String, String>? = HashMap()
    var headerParamsMap: MutableMap<String, String> = HashMap()
    var headerLinesList: MutableList<String> = ArrayList()

    private fun BasicParamsInterceptor() {}

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request? = chain.request()
        val requestBuilder = request?.newBuilder()

        // process header params inject
        val headerBuilder = request?.headers?.newBuilder()
        if (headerParamsMap.size > 0) {
            Log.e(TAG,"intercept-headerParamsMap = " + headerParamsMap)
            val iterator: Iterator<*> = headerParamsMap.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                headerBuilder?.add(entry.key as String, entry.value as String)
            }
            requestBuilder?.headers(headerBuilder!!.build())
        }
        if (headerLinesList.size > 0) {
            Log.e(TAG,"intercept-headerLinesList = " + JSON.toJSONString(headerLinesList))
            for (line in headerLinesList) {
                headerBuilder?.add(line)
            }
            requestBuilder?.headers(headerBuilder!!.build())
        }
        // process header params end


        // process queryParams inject whatever it's GET or POST
        if (queryParamsMap.size > 0) {
            Log.e(TAG,"intercept-queryParamsMap = " + queryParamsMap)
            request = injectParamsIntoUrl(request?.url?.newBuilder()!!, requestBuilder!!, queryParamsMap)
        }

        // process post body inject
        if (paramsMap != null && paramsMap!!.size > 0 && request?.method.equals("POST")) {
            Log.e(TAG,"intercept-paramsMap = " + paramsMap)
            if (request?.body is FormBody) {
                val newFormBodyBuilder = FormBody.Builder()
                if (paramsMap!!.size > 0) {
                    val iterator: Iterator<*> = paramsMap!!.entries.iterator()
                    while (iterator.hasNext()) {
                        val entry = iterator.next() as Map.Entry<*, *>
                        newFormBodyBuilder.add((entry.key as String?)!!, (entry.value as String?)!!)
                    }
                }
                val oldFormBody = request?.body as FormBody
                val paramSize = oldFormBody.size
                if (paramSize > 0) {
                    for (i in 0 until paramSize) {
                        newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i))
                    }
                }
                requestBuilder?.post(newFormBodyBuilder.build())
                request = requestBuilder?.build()
            } else if (request?.body is MultipartBody) {
                val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
                val iterator: Iterator<*> = paramsMap!!.entries.iterator()
                while (iterator.hasNext()) {
                    val entry = iterator.next() as Map.Entry<*, *>
                    multipartBuilder.addFormDataPart((entry.key as String?)!!, (entry.value as String?)!!)
                }
                val oldParts = (request?.body as MultipartBody).parts
                if (oldParts != null && oldParts.size > 0) {
                    for (part in oldParts) {
                        multipartBuilder.addPart(part)
                    }
                }
                requestBuilder?.post(multipartBuilder.build())
                request = requestBuilder?.build()
            }
        }
        return chain.proceed(request!!)
    }

    private fun canInjectIntoBody(request: Request?): Boolean {
        if (request == null) {
            return false
        }
        if (!TextUtils.equals(request.method, "POST")) {
            return false
        }
        val body = request.body ?: return false
        val mediaType = body.contentType() ?: return false
        return if (!TextUtils.equals(mediaType.subtype, "x-www-form-urlencoded")) {
            false
        } else true
    }

    // func to inject params into url
    private fun injectParamsIntoUrl(httpUrlBuilder: HttpUrl.Builder, requestBuilder: Request.Builder, paramsMap: Map<String, String>): Request? {
        if (paramsMap.size > 0) {
            val iterator: Iterator<*> = paramsMap.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                httpUrlBuilder.addQueryParameter((entry.key as String?)!!, entry.value as String?)
            }
            requestBuilder.url(httpUrlBuilder.build())
            return requestBuilder.build()
        }
        return null
    }

    private fun bodyToString(request: RequestBody): String? {
        return try {
            val buffer = Buffer()
            if (request != null) request.writeTo(buffer) else return ""
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    class Builder {
        var interceptor: BasicParamsInterceptor
        fun addParam(key: String, value: String): Builder {
            interceptor.paramsMap!![key] = value
            return this
        }

        fun addParamsMap(paramsMap: Map<String, String>?): Builder {
            interceptor.paramsMap!!.putAll(paramsMap!!)
            return this
        }

        fun addHeaderParam(key: String, value: String): Builder {
            interceptor.headerParamsMap[key] = value
            return this
        }

        fun addHeaderParamsMap(headerParamsMap: Map<String, String>?): Builder {
            interceptor.headerParamsMap.putAll(headerParamsMap!!)
            return this
        }

        fun addHeaderLine(headerLine: String): Builder {
            val index = headerLine.indexOf(":")
            require(index != -1) { "Unexpected header: $headerLine" }
            interceptor.headerLinesList.add(headerLine)
            return this
        }

        fun addHeaderLinesList(headerLinesList: List<String>): Builder {
            for (headerLine in headerLinesList) {
                val index = headerLine.indexOf(":")
                require(index != -1) { "Unexpected header: $headerLine" }
                interceptor.headerLinesList.add(headerLine)
            }
            return this
        }

        fun addQueryParam(key: String, value: String): Builder {
            interceptor.queryParamsMap[key] = value
            return this
        }

        fun addQueryParamsMap(queryParamsMap: Map<String, String>?): Builder {
            interceptor.queryParamsMap.putAll(queryParamsMap!!)
            return this
        }

        fun build(): BasicParamsInterceptor {
            return interceptor
        }

        init {
            interceptor = BasicParamsInterceptor()
        }
    }
}