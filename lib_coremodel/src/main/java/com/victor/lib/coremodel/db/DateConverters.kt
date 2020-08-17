package com.victor.lib.coremodel.db

import androidx.room.TypeConverter
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Converters
 * Author: Victor
 * Date: 2020/8/17 上午 10:27
 * Description: Type converters to allow Room to reference complex data types.
 * -----------------------------------------------------------------
 */

class DateConverters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}