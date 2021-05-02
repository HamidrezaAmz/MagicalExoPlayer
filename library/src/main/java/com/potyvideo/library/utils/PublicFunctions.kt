package com.potyvideo.library.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.potyvideo.library.utils.PublicValues.Companion.REQUEST_ID_MULTIPLE_PERMISSIONS


class PublicFunctions {

    companion object {

        fun isThisSourceSupported(source: String): Boolean {
            val mimeType = getMimeType(source)
            return PublicValues.SUPPORTED_MEDIAS.contains(mimeType)
        }

        fun getMimeType(source: String): String {
            return source.substringAfterLast(".", PublicValues.KEY_UNKNOWN).toLowerCase()
        }

        fun getScreenWidth(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            return Resources.getSystem().displayMetrics.heightPixels
        }

        fun convertDpToPixel(context: Context, dp: Float): Int {
            return (dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)) as Int
        }

        fun convertPixelsToDp(context: Context, px: Float): Int {
            return (px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)) as Int
        }

        fun checkAccessStoragePermission(activity: Activity?): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val listPermissionsNeeded: MutableList<String> = ArrayList()
                if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                if (listPermissionsNeeded.isNotEmpty()) {
                    ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
                    return false
                }
            }
            return true
        }
    }
}