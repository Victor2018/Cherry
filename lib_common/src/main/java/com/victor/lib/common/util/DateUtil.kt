package com.victor.lib.common.util

import android.text.TextUtils
import android.text.format.Time
import com.victor.lib.coremodel.util.Loger
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DateUtil
 * Author: Victor
 * Date: 2020/7/21 上午 11:54
 * Description: 
 * -----------------------------------------------------------------
 */
object DateUtil {
    private const val TAG = "DateUtils"

    /**
     * 获取当前日期
     * @return
     */
    fun getCurrentTime(): String? {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return df.format(Date())
    }

    fun transDate(
        inputDate: String,
        inputFormat: String?,
        outFormat: String?
    ): String {
        var date = ""
        if (TextUtils.isEmpty(inputDate)) return date
        val format = SimpleDateFormat(inputFormat)
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        Loger.e(TAG, "inputDate = $inputDate")
        val formatter = SimpleDateFormat(outFormat)
        date = formatter.format(c.time)
        Loger.e(TAG, "date = $date")
        return date
    }

    /**
     * 获取当前时间的年
     * @return
     */
    fun getNowYear(): String? {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy")
        return formatter.format(date)
    }

    /**
     * 获取当前时间的小时数
     * @return
     */
    fun getNowHour(): Int {
        val date = Date()
        val formatter = SimpleDateFormat("HH")
        val today = formatter.format(date)
        var hour = 0
        try {
            hour = today.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return hour
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    fun getDaysByYearMonth(year: Int, month: Int): Int {
        val a = Calendar.getInstance()
        a[Calendar.YEAR] = year
        a[Calendar.MONTH] = month - 1
        a[Calendar.DATE] = 1
        a.roll(Calendar.DATE, -1)
        return a[Calendar.DATE]
    }

    /**
     * 获取今天日期
     * @return
     */
    fun getTodayDate(): String? {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy年MM月dd")
        val today = formatter.format(date)
        Loger.e(TAG, "getDateOfToday-today = $today")
        return today
    }

    /**
     * 获取今天日期
     * @return
     */
    fun getTodayDate(formater: String?): String {
        val date = Date()
        val formatter = SimpleDateFormat(formater)
        val today = formatter.format(date)
        Loger.e(TAG, "getDateOfToday-today = $today")
        return today
    }

    /**
     * @param m 分钟差值
     * @return
     */
    fun ms2CreateTime(m: Long): String? {
        Loger.e(TAG, "ms2ActiveTime-min = $m")
        var res = ""
        var day: Long = 0
        var hour: Long = 0
        var min: Long = 0
        val ms = m * 60 * 1000
        day = ms / (24 * 60 * 60 * 1000)
        hour = (ms - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        min = (ms - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)
        if (day > 0) {
            res = day.toString() + "天"
        } else if (hour > 0) {
            res = hour.toString() + "小时"
        } else if (min > 0) {
            res = min.toString() + "分钟"
        }
        Loger.e(TAG, "ms2ActiveTime-res = $res")
        return res
    }

    /**
     * @param ms 时间戳差值
     * @return
     */
    fun ms2SysCreateTime(ms: Long): String? {
        Loger.e(TAG, "ms2ActiveTime-ms = $ms")
        var res = ""
        var day: Long = 0
        var hour: Long = 0
        var min: Long = 0
        day = ms / (24 * 60 * 60 * 1000)
        hour = (ms - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        min = (ms - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)
        if (day > 0) {
            res = day.toString() + "天前"
        } else if (hour > 0) {
            res = hour.toString() + "小时前"
        } else if (min > 0) {
            res = min.toString() + "分钟前"
        }
        Loger.e(TAG, "ms2SysCreateTime-res = $res")
        return res
    }

    /**
     * @param time 日期
     * @return
     */
    fun handeDynamicTime(time: String): String? {
        Loger.e(TAG, "handeDynamicTime-time = $time")
        var res = ""
        val createTime: Long = dateToStamp(time)
        val currentTime: Long = getTimestamp()
        val diff = currentTime - createTime
        val days = diff / (1000 * 60 * 60 * 24)
        val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes =
            (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        if (days > 0) {
            res = if (days > 3) {
                transDate(time, "yyyy-MM-dd HH:mm:ss", "MM-dd HH:mm")
            } else {
                days.toString() + "天前"
            }
        } else if (hours > 0) {
            res = hours.toString() + "小时前"
        } else if (minutes > 0) {
            res = if (minutes < 30) {
                "刚刚"
            } else {
                minutes.toString() + "分钟前"
            }
        }
        Loger.e(TAG, "ms2SysCreateTime-res = $res")
        return res
    }

    /**
     * @param time 日期
     * @return
     */
    fun handeSysMessageTime(time: String): String? {
        Loger.e(TAG, "handeSysMessageTime-time = $time")
        var res = ""
        val createTime: Long = dateToStamp(time)
        val currentTime: Long = getTimestamp()
        val diff = currentTime - createTime
        val days = diff / (1000 * 60 * 60 * 24)
        val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes =
            (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        if (days > 0) {
            res = if (days > 3) {
                transDate(time, "yyyy-MM-dd HH:mm:ss", "MM-dd HH:mm")
            } else {
                days.toString() + "天前"
            }
        } else if (hours > 0) {
            res = hours.toString() + "小时前"
        } else if (minutes > 0) {
            res = if (minutes < 5) {
                "刚刚"
            } else {
                minutes.toString() + "分钟前"
            }
        }
        Loger.e(TAG, "ms2SysCreateTime-res = $res")
        return res
    }

    /**
     * 获取当前时间戳
     * @return
     */
    fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }

    /*
     * 将时间转换为时间戳
     */
    fun dateToStamp(s: String): Long {
        Loger.e(TAG, "dateToStamp-s = $s")
        var timeStamp: Long = 0
        if (TextUtils.isEmpty(s)) return timeStamp
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val date = simpleDateFormat.parse(s)
            timeStamp = date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeStamp
    }


    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(s: Long): String? {
        val res: String
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(s)
        res = simpleDateFormat.format(date)
        return res
    }

    /**
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return
     */
    fun isToday(day: String?): Boolean {
        try {
            val pre = Calendar.getInstance()
            val predate = Date(System.currentTimeMillis())
            pre.time = predate
            val cal = Calendar.getInstance()
            val date = getDateFormat()!!.parse(day)
            cal.time = date
            if (cal[Calendar.YEAR] == pre[Calendar.YEAR]) {
                val diffDay = (cal[Calendar.DAY_OF_YEAR]
                        - pre[Calendar.DAY_OF_YEAR])
                if (diffDay == 0) {
                    return true
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun isBeforeTodayDate(date: String?, formater: String?): Boolean {
        val today = getTodayDate(formater)
        val df: DateFormat = SimpleDateFormat(formater)
        try {
            val date1 = df.parse(date)
            val date2 = df.parse(today)
            if (date1.time < date2.time) {
                return true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return false
    }

    /**
     * 判断当前系统时间是否在特定时间的段内
     *
     * @param beginHour 开始的小时，例如5
     * @param beginMin 开始小时的分钟数，例如00
     * @param endHour 结束小时，例如 8
     * @param endMin 结束小时的分钟数，例如00
     * @return true表示在范围内，否则false
     */
    fun isCurrentInTimeScope(
        beginHour: Int,
        beginMin: Int,
        endHour: Int,
        endMin: Int
    ): Boolean {
        var result = false // 结果
        val aDayInMillis = 1000 * 60 * 60 * 24.toLong() // 一天的全部毫秒数
        val currentTimeMillis = System.currentTimeMillis() // 当前时间
        val now =
            Time() // 注意这里导入的时候选择android.text.format.Time类,而不是java.sql.Time类
        now.set(currentTimeMillis)
        val startTime = Time()
        startTime.set(currentTimeMillis)
        startTime.hour = beginHour
        startTime.minute = beginMin
        val endTime = Time()
        endTime.set(currentTimeMillis)
        endTime.hour = endHour
        endTime.minute = endMin
        if (!startTime.before(endTime)) {
            // 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis)
            result = !now.before(startTime) && !now.after(endTime) // startTime <= now <= endTime
            val startTimeInThisDay = Time()
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis)
            if (!now.before(startTimeInThisDay)) {
                result = true
            }
        } else {
            // 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime) // startTime <= now <= endTime
        }
        return result
    }

    /**
     * @param time 日期
     * @return
     */
    fun moreThanOneMon(time: String): Boolean {
        Loger.e(TAG, "moreThanOneMon-time = $time")
        val res = ""
        val createTime =
            dateToStamp(transDate(time, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"))
        val currentTime = getTimestamp()
        val diff = createTime - currentTime
        val days = diff / (1000 * 60 * 60 * 24)
        Loger.e(TAG, "moreThanOneMon-days = $days")
        return days > 31
    }


    fun getDateFormat(): SimpleDateFormat? {
        if (null == DateLocal.get()) {
            DateLocal.set(SimpleDateFormat("yyyy-MM-dd", Locale.CHINA))
        }
        return DateLocal.get()
    }

    private val DateLocal =
        ThreadLocal<SimpleDateFormat?>()
}