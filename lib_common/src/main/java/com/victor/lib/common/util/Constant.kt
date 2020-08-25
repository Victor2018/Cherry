package com.victor.lib.common.util

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Constant
 * Author: Victor
 * Date: 2020/7/15 上午 11:59
 * Description: 
 * -----------------------------------------------------------------
 */
object Constant {
    const val HEADER_HIDE_ANIM_DURATION         = 300
    const val BG_COLOR_MAX                      = 255f
    const val BG_COLOR_MIN                      = 232f
    const val INTENT_DATA_KEY                   = "INTENT_DATA_KEY"
    const val INTENT_ACTION_KEY                 = "CATEGORY_TYPE_KEY"
    const val CATEGORY_TYPE_KEY             = "CATEGORY_POSITION_KEY"
    const val MA_DATA = "madata"
    const val SHARE_TYPE                        = "text/plain";
    const val MAIL_TO                           = "mailto:%s";

    object Msg {
        const val ENTER_HAPPY_COAST             = 0x1001
        const val TIME_CHANGE                   = 0x1002
    }
    object Action {
        const val SHOW_NAV_BAR                  = 0x2001
        const val HIDE_NAV_BAR                  = 0x2002
        const val GO_MAIN                       = 0x2003
    }

}