package io.shivamvk.imgur_androidexample.utils

import android.Manifest
import androidx.core.app.ActivityCompat

class Permissions {
    var cameraPermission:String = Manifest.permission.CAMERA
    var cameraPermissionCode:Int = 100
    var writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    var writeExternalStoragePermissionCode:Int = 101
    var internetPermission = Manifest.permission.INTERNET
}