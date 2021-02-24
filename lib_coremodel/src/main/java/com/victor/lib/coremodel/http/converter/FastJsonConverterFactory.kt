package com.victor.lib.coremodel.http.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.alibaba.fastjson.parser.ParserConfig
import com.alibaba.fastjson.serializer.SerializeConfig
import com.alibaba.fastjson.serializer.SerializerFeature
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FastJsonConverterFactory
 * Author: Victor
 * Date: 2021/2/24 14:17
 * Description: A [converter][Converter.Factory] which uses FastJson for JSON.
 *
 * Because FastJson is so flexible in the types it supports, this converter assumes that it can
 * handle all types. If you are mixing JSON serialization with something else (such as protocol
 * buffers), you must [add][Retrofit.Builder.addConverterFactory] last to allow the other converters
 * a chance to see their types.
 * -----------------------------------------------------------------
 */
class FastJsonConverterFactory private constructor() : Converter.Factory() {
    var parserConfig = ParserConfig.getGlobalInstance()
    var parserFeatureValues = JSON.DEFAULT_PARSER_FEATURE
    var parserFeatures: Array<out Feature>? = null
    var serializeConfig: SerializeConfig? = null
    var serializerFeatures: Array<out SerializerFeature>? = null

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return FastJsonResponseBodyConverter<Any>(
            type,
            parserConfig,
            parserFeatureValues, parserFeatures
        )
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): FastJsonRequestBodyConverter<Any> {
        return FastJsonRequestBodyConverter<Any>(serializeConfig, serializerFeatures)
    }

    fun setParserConfig(config: ParserConfig): FastJsonConverterFactory {
        parserConfig = config
        return this
    }

    fun setParserFeatureValues(featureValues: Int): FastJsonConverterFactory {
        parserFeatureValues = featureValues
        return this
    }

    fun setParserFeatures(features: Array<Feature>): FastJsonConverterFactory {
        parserFeatures = features
        return this
    }

    fun setSerializeConfig(serializeConfig: SerializeConfig?): FastJsonConverterFactory {
        this.serializeConfig = serializeConfig
        return this
    }

    fun setSerializerFeatures(features: Array<out SerializerFeature>): FastJsonConverterFactory {
        serializerFeatures = features
        return this
    }

    companion object {
        /**
         * Create an default instance for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         * @return The instance of FastJsonConverterFactory
         */
        fun create(): FastJsonConverterFactory {
            return FastJsonConverterFactory()
        }
    }
}
