package com.testask.kiosktabletapp.data

import android.app.Activity
import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.UserManager
import android.util.Log


object KioskHandler {
    var mAdminComponentName: ComponentName? = null
    var mDevicePolicyManager: DevicePolicyManager? = null

    fun startBlockMode(context: Context) {
        val activity = context as Activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startLockTask()
        }
    }

    fun stopBlockMode(context: Activity) {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        try {
            if (am.lockTaskModeState == ActivityManager.LOCK_TASK_MODE_LOCKED) {
                context.stopLockTask()
                setDefaultCosuPolicies(false)
            }
        } catch (e: Exception) {
            Log.e("KIOSK_DEBUG", "Error kiosk mode: ${e.localizedMessage}")
        }
    }

    fun initialize(context: Context): Boolean {
        try {
            mAdminComponentName = ComponentName(context, MyDeviceAdminReceiver::class.java)
            mDevicePolicyManager =
                context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val packages = arrayOf(
                context.packageName,
                "com.android.date",
                "com.android.settings.date",
                "com.android.bluetooth",
                "com.bluetooth",
                "com.Bluetooth",
                "com.samsung",
                "com.wifi",
                "com.android.wifi",
                "com.android.settings.bluetooth",
                "com.android.settings.wifi"
            )
            mDevicePolicyManager!!.setLockTaskPackages(mAdminComponentName, packages)
            if (mDevicePolicyManager!!.isDeviceOwnerApp(context.packageName)) {
                setDefaultCosuPolicies(true)
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
    }

    private fun setDefaultCosuPolicies(active: Boolean) {
        setUserRestriction(UserManager.DISALLOW_SAFE_BOOT, active)
        setUserRestriction(UserManager.DISALLOW_FACTORY_RESET, active)
        setUserRestriction(UserManager.DISALLOW_ADD_USER, active)
        setUserRestriction(UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA, active)
        setUserRestriction(UserManager.DISALLOW_USB_FILE_TRANSFER, active)
        mDevicePolicyManager!!.setKeyguardDisabled(mAdminComponentName!!, active)
        mDevicePolicyManager!!.setStatusBarDisabled(mAdminComponentName, active)
    }

    private fun setUserRestriction(restriction: String, disallow: Boolean) {
        if (disallow) {
            mDevicePolicyManager!!.addUserRestriction(mAdminComponentName!!, restriction)
        } else {
            mDevicePolicyManager!!.clearUserRestriction(mAdminComponentName!!, restriction)
        }
    }
}