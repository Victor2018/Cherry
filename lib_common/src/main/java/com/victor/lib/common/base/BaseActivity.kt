package com.victor.lib.common.base

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.R
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.StatusBarUtil
import permission.victor.com.library.OnPermissionCallback
import permission.victor.com.library.PermissionHelper
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseActivity
 * Author: Victor
 * Date: 2020/7/3 下午 06:17
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseActivity: AppCompatActivity(), OnPermissionCallback {
    protected var TAG = javaClass.simpleName
    var statusBarTextColorBlack: Boolean = false

    var permissionHelper: PermissionHelper? = null
    private var neededPermission: Array<String>? = null
    private var builder: AlertDialog? = null
    private val MULTI_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
        Manifest.permission.ACCESS_WIFI_STATE)

    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        initializeSuper()
    }

    fun initializeSuper () {
        StatusBarUtil.translucentStatusBar(this, true,statusBarTextColorBlack,true)
        if (StatusBarUtil.hasNavigationBarShow(this)) {
            window.decorView.findViewById<View>(android.R.id.content).setPadding(0, 0, 0, StatusBarUtil.getNavigationBarHeight(this));
        }
        permissionHelper = PermissionHelper.getInstance(this);
    }

    fun isPermissionGranted (permissionName: String): Boolean {
        var isPermissionGranted = permissionHelper?.isPermissionGranted(permissionName)
        Loger.e(TAG,"isPermissionGranted = " + isPermissionGranted!!)
        return isPermissionGranted!!
    }

    fun requestPermission (permission: String) {
        Loger.e(TAG,"requestPermission-permission = " + permission)
        permissionHelper?.setForceAccepting(false) // default is false. its here so you know that it exists.
            ?.request(arrayOf(permission));
    }
    fun requestPermission (permissions: Array<String>) {
        Loger.e(TAG,"requestPermission-permissions = " + permissions.toString())
        permissionHelper?.setForceAccepting(false) // default is false. its here so you know that it exists.
            ?.request(permissions);
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_activity_enter_back, R.anim.anim_activity_exit_back);
    }

    override fun onPermissionPreGranted(permissionsName: String) {
        Loger.d("onPermissionPreGranted", "Permission( " + permissionsName + " ) preGranted");
    }

    override fun onPermissionGranted(permissionName: Array<out String>) {
        Loger.d(TAG, "onPermissionGranted-Permission(s) " + Arrays.toString(permissionName) + " Granted");
    }

    override fun onNoPermissionNeeded() {
        Loger.d("onNoPermissionNeeded", "Permission(s) not needed");
    }

    override fun onPermissionReallyDeclined(permissionName: String) {
        Loger.d("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");
    }

    override fun onPermissionDeclined(permissionName: Array<out String>) {
        Loger.d("onPermissionDeclined", "Permission(s) " + Arrays.toString(permissionName) + " Declined");
    }

    override fun onPermissionNeedExplanation(p0: String) {
        neededPermission = PermissionHelper.declinedPermissions(this, MULTI_PERMISSIONS)
        val builder = StringBuilder(neededPermission!!.size)
        if (neededPermission!!.size > 0) {
            for (permission in neededPermission!!) {
                builder.append(permission).append("\n")
            }
        }
        val alert = getAlertDialog(neededPermission!!, builder.toString())
        if (!alert.isShowing()) {
            alert.show()
        }
    }

    fun getAlertDialog(permissions: Array<String>, permissionName: String): AlertDialog {
        if (builder == null) {
            builder = AlertDialog.Builder(this)
                .setTitle("Permission Needs Explanation")
                .create()
        }
        builder?.setButton(DialogInterface.BUTTON_POSITIVE, "Request", DialogInterface.OnClickListener { dialog, which -> permissionHelper?.requestAfterExplanation(permissions) })
        builder?.setMessage("Permissions need explanation ($permissionName)")
        return builder!!
    }
}