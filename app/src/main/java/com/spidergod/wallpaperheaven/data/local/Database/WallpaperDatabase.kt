package com.spidergod.wallpaperheaven.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spidergod.wallpaperheaven.data.local.dao.WallpaperFavDao
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity

@Database(entities = [WallpaperEntity::class], version = 1)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpaperFavDao(): WallpaperFavDao
}