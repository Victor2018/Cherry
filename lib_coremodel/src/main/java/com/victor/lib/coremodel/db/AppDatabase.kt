package com.victor.lib.coremodel.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.victor.lib.coremodel.dao.ChannelDao
import com.victor.lib.coremodel.db.DbConfig.DATABASE_NAME
import com.victor.lib.coremodel.data.Category
import com.victor.lib.coremodel.data.ChannelInfo
import com.victor.lib.coremodel.data.GankDetailInfo
import com.victor.lib.coremodel.db.dao.CategoryDao
import com.victor.lib.coremodel.db.dao.GirlsDao
import com.victor.lib.coremodel.workers.SeedDatabaseWorker

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppDatabase
 * Author: Victor
 * Date: 2020/8/12 下午 06:26
 * Description: 
 * -----------------------------------------------------------------
 */

@Database(entities = arrayOf(
    Category::class,
    ChannelInfo::class,
    GankDetailInfo:: class
),
    version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun channelCategoryDao(): CategoryDao
    abstract fun channelDao(): ChannelDao
    abstract fun girlsDao(): GirlsDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}