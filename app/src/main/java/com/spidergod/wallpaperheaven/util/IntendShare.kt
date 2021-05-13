package com.spidergod.wallpaperheaven.util

import android.content.Context
import android.content.Intent


fun ShareLinkWithOtherApps(context: Context, message: String) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, message)
    sendIntent.type = "text/plain"
    context.startActivity(sendIntent)
}