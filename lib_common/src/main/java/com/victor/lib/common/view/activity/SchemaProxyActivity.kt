package com.victor.lib.common.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.module.SchemaModule

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SchemaProxyActivity
 * Author: Victor
 * Date: 2020/7/27 下午 05:33
 * Description: 
 * -----------------------------------------------------------------
 */
class SchemaProxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val temp = intent.data
        dispatchScheme(temp)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        dispatchScheme(intent?.data)
    }

    private fun dispatchScheme(data: Uri?) {
        SchemaModule.dispatchSchema(this, data)
        finish()
    }
}