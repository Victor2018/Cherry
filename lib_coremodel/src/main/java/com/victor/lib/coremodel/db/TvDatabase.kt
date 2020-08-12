package com.victor.lib.coremodel.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.victor.lib.coremodel.dao.ChannelCategoryDao
import com.victor.lib.coremodel.db.TvDbConfig.DATABASE_NAME
import com.victor.lib.coremodel.entity.ChannelCategory
import com.victor.lib.coremodel.workers.SeedDatabaseWorker

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TvDatabase
 * Author: Victor
 * Date: 2020/8/12 下午 06:26
 * Description: 
 * -----------------------------------------------------------------
 */

@Database(entities = arrayOf(ChannelCategory::class), version = 1)
abstract class TvDatabase: RoomDatabase() {
    abstract fun channelCategoryDao(): ChannelCategoryDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: TvDatabase? = null

        fun getInstance(context: Context): TvDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): TvDatabase {
            return Room.databaseBuilder(context, TvDatabase::class.java, DATABASE_NAME)
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