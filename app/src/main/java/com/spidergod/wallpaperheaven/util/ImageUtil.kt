package com.spidergod.wallpaperheaven.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


fun createPlaceHolder(width: Int, height: Int, color: Int): Bitmap? {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    paint.setColor(color)
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return bitmap
}

@Composable
fun loadPicture(
    url: String,
    dimx: Int,
    dimy: Int,
    color: Int,
): Bitmap? {
    val requiredRes = imageResize(dimx, dimy)
    var bitmapState by remember {
        mutableStateOf(createPlaceHolder(requiredRes.first, requiredRes.second, color))
    }
    val context = LocalContext.current


    Glide.with(context).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            bitmapState = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
    })

    return bitmapState
}


fun imageResize(orginalW: Int, orginalH: Int): Pair<Int, Int> {

    if (orginalW > orginalH) {
        val newH = orginalH * 300 / orginalW
        return Pair(300, newH)
    } else {
        val newW = orginalW * 300 / orginalH
        return Pair(newW, 300)
    }

}