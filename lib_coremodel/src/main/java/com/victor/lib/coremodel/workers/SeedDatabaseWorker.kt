package com.victor.lib.coremodel.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.victor.lib.coremodel.db.TvDatabase
import com.victor.lib.coremodel.db.TvDbConfig.TV_DATA_FILENAME
import com.victor.lib.coremodel.entity.ChannelCategory
import kotlinx.coroutines.coroutineScope

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SeedDatabaseWorker
 * Author: Victor
 * Date: 2020/8/12 下午 06:28
 * Description: 
 * -----------------------------------------------------------------
 */

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(TV_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<ChannelCategory>>() {}.type
                    val plantList: List<ChannelCategory> = Gson().fromJson(jsonReader, plantType)

                    val database = TvDatabase.getInstance(applicationContext)
                    database.channelCategoryDao().insertAll(plantList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}