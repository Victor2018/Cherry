package com.victor.lib.coremodel.http.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import com.alibaba.fastjson.serializer.SerializerFeature
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Converter
import java.io.IOException


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FastJsonRequestBodyConverter
 * Author: Victor
 * Date: 2021/2/24 14:18
 * Description: 
 * -----------------------------------------------------------------
 */
class FastJsonRequestBodyConverter<T>(
    config: SerializeConfig?,
    var features: Array<out SerializerFeature>?
) :
    Converter<T, RequestBody?> {
    private val serializeConfig: SerializeConfig?
    private val serializerFeatures: Array<out SerializerFeature>?

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val content: ByteArray
        content = if (serializeConfig != null) {
            if (serializerFeatures != null) {
                JSON.toJSONBytes(value, serializeConfig, *serializerFeatures)
            } else {
                JSON.toJSONBytes(value, serializeConfig)
            }
        } else {
            if (serializerFeatures != null) {
                JSON.toJSONBytes(value, *serializerFeatures)
            } else {
                JSON.toJSONBytes(value)
            }
        }
        return RequestBody.create(MEDIA_TYPE, content)
    }

    companion object {
        private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaTypeOrNull()
    }

    init {
        serializeConfig = config
        serializerFeatures = features
    }
}
