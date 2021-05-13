package com.spidergod.wallpaperheaven.util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings

fun handleDownload(context: Context, fromUrl: String?, toFilename: String?): Boolean {
    if (Build.VERSION.SDK_INT < 9) {
        throw RuntimeException("Method requires API level 9 or above")
    }
    val request = DownloadManager.Request(Uri.parse(fromUrl))
    if (Build.VERSION.SDK_INT >= 11) {
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        "/WallpaperHeaven/$toFilename"
    )
    val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    return try {
        try {
            dm.enqueue(request)
        } catch (e: SecurityException) {
            if (Build.VERSION.SDK_INT >= 11) {
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            }
            dm.enqueue(request)
        }
        true
    } // if the download manager app has been disabled on the device
    catch (e: IllegalArgumentException) {
        // show the settings screen where the user can enable the download manager app again
        openAppSettings(context, "com.android.providers.downloads")
        false
    }
}

private fun openAppSettings(context: Context, packageName: String): Boolean {
    if (Build.VERSION.SDK_INT < 9) {
        throw RuntimeException("Method requires API level 9 or above")
    }
    return try {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }

}

