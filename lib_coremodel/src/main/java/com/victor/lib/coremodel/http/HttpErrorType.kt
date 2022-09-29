package com.victor.lib.coremodel.http

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpError
 * Author: Victor
 * Date: 2022/5/6 17:02
 * Description: 
 * -----------------------------------------------------------------
 */

object HttpErrorType {
    /**
     * Represents failure response (non-2xx) with body.
     */
    const val API = 0x423001

    /**
     *  Represents network failure (such as no internet connection).
     */
    const val NETWORK = 0x423002

    /**
     * Represents unexpected exceptions (for example parsing issues).
     */
    const val UNKNOWN = 0x423003

    /**
     * server error (such as body == null or errorBody == null).
     */
    const val SERVER = 0x423004
}