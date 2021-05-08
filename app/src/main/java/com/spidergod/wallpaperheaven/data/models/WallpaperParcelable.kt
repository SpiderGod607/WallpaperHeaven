package com.spidergod.wallpaperheaven.data.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
class WallpaperParcelable(
    val id: String,
    val url: String,
    val source: String,
    val category: String,
    val dimension_x: Int,
    val dimension_y: Int,
    val resolution: String,
    val ratio: String,
    val fileType: String,
    val page: Int,
    val cacheTag: String,
    val color: String,
    val urlHigh: String
) : Parcelable