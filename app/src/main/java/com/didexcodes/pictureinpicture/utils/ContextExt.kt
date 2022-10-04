package com.didexcodes.pictureinpicture.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build
import com.didexcodes.pictureinpicture.presentation.MainActivity

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun Context.mainActivity(): MainActivity {
    return findActivity() as MainActivity
}

fun Context.isPipSupported(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    } else {
        false
    }
}