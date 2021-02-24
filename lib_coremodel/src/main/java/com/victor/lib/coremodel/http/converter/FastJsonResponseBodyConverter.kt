package com.victor.lib.coremodel.http.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.alibaba.fastjson.parser.ParserConfig
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FastJsonResponseBodyConverter
 * Author: Victor
 * Date: 2021/2/24 14:19
 * Description: 
 * -----------------------------------------------------------------
 */
internal class FastJsonResponseBodyConverter<T>(
    type: Type, config: ParserConfig, featureValues: Int,
    var feature: Array<out Feature>?
) : Converter<ResponseBody, T?> {
    private var mType: Type
    private var config: ParserConfig
    private var featureValues: Int
    private var features: Array<out Feature>?

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {

        return try {
            JSON.parseObject(
                value.string(), mType, config, featureValues,
                *features ?: EMPTY_SERIALIZER_FEATURES
            )
        } finally {
            value.close()
        }
    }

    companion object {
        private val EMPTY_SERIALIZER_FEATURES =
            arrayOfNulls<Feature>(0)
    }

    init {
        mType = type
        this.config = config
        this.featureValues = featureValues
        this.features = feature
    }
}