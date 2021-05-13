package com.spidergod.wallpaperheaven.util

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


class SetWallpaperUtil @Inject constructor(var appContext: Application) {

    suspend fun getBitmapFromURL(src: String?): Bitmap? {
        return withContext(Dispatchers.IO) {
            var bitmap: Bitmap? = null
            try {
                val url = URL(src)
                val connection: HttpURLConnection = url
                    .openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: java.lang.Exception) {
            }
            bitmap
        }
    }

    suspend fun setWallpaper(url: String): Boolean {
        return withContext(Dispatchers.IO) {
            val originalImageBitmap = getBitmapFromURL(url) ?: return@withContext false
            val wallpaperManager = WallpaperManager.getInstance(appContext)

            try {
                wallpaperManager.setBitmap(originalImageBitmap)
            } catch (e: Exception) {
                return@withContext false
            }

            try {
                originalImageBitmap.recycle()

            } catch (e: Exception) {
            }
            true
        }
    }

}