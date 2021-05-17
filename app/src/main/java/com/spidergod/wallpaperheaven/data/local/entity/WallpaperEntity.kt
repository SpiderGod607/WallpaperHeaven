package com.spidergod.wallpaperheaven.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wallpaperFavTable")
data class WallpaperEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "source")
    val source: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "dimension_x")
    val dimension_x: Int,
    @ColumnInfo(name = "dimension_y")
    val dimension_y: Int,
    @ColumnInfo(name = "resolution")
    val resolution: String,
    @ColumnInfo(name = "ratio")
    val ratio: String,
    @ColumnInfo(name = "fileType")
    val fileType: String,
    @ColumnInfo(name = "page")
    val page: Int,
    @ColumnInfo(name = "cacheTag")
    val cacheTag: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "urlHigh")
    val urlHigh: String,
    @ColumnInfo(name = "urlShort")
    val urlShort: String
)
