package io.shivamvk.imgur_androidexample.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionManager {

    fun checkForPermission(context: Context, permission: String): Boolean{
        return (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestForPermission(activity: Activity, permission: String){
        when(permission){
            Manifest.permission.CAMERA -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    Permissions().cameraPermissionCode
                )
                return
            }
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    Permissions().writeExternalStoragePermissionCode
                )
                return
            }
            Manifest.permission.INTERNET -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    102
                )
                return
            }
        }
    }

}