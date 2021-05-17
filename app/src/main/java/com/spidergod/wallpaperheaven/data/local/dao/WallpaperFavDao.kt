package com.spidergod.wallpaperheaven.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity

@Dao
interface WallpaperFavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoFav(wallpaperEntity: WallpaperEntity)

    @Delete
    suspend fun removeFav(wallpaperEntity: WallpaperEntity)

    @Query("SELECT id FROM wallpaperFavTable")
    fun getAllFavNames(): LiveData<List<String>>

    @Query("SELECT EXISTS(SELECT * FROM wallpaperFavTable WHERE id = :id)")
    fun isRowIsExist(id: String): Boolean

    @Query("SELECT * FROM wallpaperFavTable")
    fun getAllWallpaper(): LiveData<List<WallpaperEntity>>

}