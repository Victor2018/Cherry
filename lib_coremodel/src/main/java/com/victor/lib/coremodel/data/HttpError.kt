package com.victor.lib.coremodel.data


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpError
 * Author: Victor
 * Date: 2021/2/24 14:25
 * Description: 
 * -----------------------------------------------------------------
 */
data class HttpError(
    var statusCode: String,
    var statusMessage: String
)