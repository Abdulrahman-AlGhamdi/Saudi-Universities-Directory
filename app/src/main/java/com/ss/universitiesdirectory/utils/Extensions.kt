package com.ss.universitiesdirectory.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun connectTo(address: String): Any {
    return try {
        (URL(address).openConnection() as HttpsURLConnection).apply {
            this.requestMethod = "GET"
            this.connectTimeout = 15000
            this.readTimeout = 15000
            this.doInput = true
        }
    } catch (e: MalformedURLException) {
        e.printStackTrace()
        "Error : wrong url format"
    } catch (e: IOException) {
        e.printStackTrace()
        "Error : unable to establish connection"
    }
}

fun View.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    anchorView: Int? = null,
    actionMessage: String? = null,
    action: (View) -> Unit = {}
) {
    Snackbar.make(this, message, length).apply {
        actionMessage?.let { this.setAction(actionMessage) { action(it) } }
        anchorView?.let { this.setAnchorView(anchorView) }
    }.show()
}

fun Activity.keepScreenOn(keep: Boolean) {
    if (keep) this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    else this.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun Fragment.checkPermission(permission: String, isGranted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED)
        isGranted(true) else isGranted(false)
}

fun Context.clearApplicationUserData() {
    val activityManager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.clearApplicationUserData()
}

fun NavController.navigateTo(action: NavDirections, fragmentId: Int) {
    if (this.currentDestination == this.graph.findNode(fragmentId))
        this.navigate(action)
}