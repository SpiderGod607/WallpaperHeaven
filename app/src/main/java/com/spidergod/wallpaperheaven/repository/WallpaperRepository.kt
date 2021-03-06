package com.spidergod.wallpaperheaven.repository

import androidx.lifecycle.LiveData
import com.spidergod.wallpaperheaven.data.local.Database.WallpaperDatabase
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.data.remote.api.WallpaperApi
import com.spidergod.wallpaperheaven.data.remote.response_models.wallpaper_list_model_response.WallpaperListReponseDto
import com.spidergod.wallpaperheaven.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WallpaperRepository @Inject constructor(
    private val wallpaperApi: WallpaperApi,
    private val database: WallpaperDatabase
) {
    val dao = database.wallpaperFavDao()

    suspend fun getWallpaperList(
        query: String = "",
        categories: String = "111",
        purity: String = "100",
        topRange: String = "1M",
        sorting: String = "toplist",
        order: String = "desc",
        page: Int
    ): Resource<WallpaperListReponseDto> {

        val response = try {
            wallpaperApi.getWallpaperList(
                categories = categories,
                purity = purity,
                topRange = topRange,
                sorting = sorting,
                order = order,
                page = page,
                query = query
            )

        } catch (e: Exception) {
            return Resource.Error("${e.localizedMessage}")
        }
        return Resource.Success(response)
    }

    suspend fun getSimilarWallpaper(wallpaperId: String): Resource<WallpaperListReponseDto> {
        val respose = try {
            wallpaperApi.getSimilarWallpaper("like:$wallpaperId")
        } catch (e: Exception) {
            return Resource.Error("${e.localizedMessage}")
        }
        return Resource.Success(respose)
    }

    fun getAllFavLIstNames(): LiveData<List<String>> {
        return dao.getAllFavNames()
    }

    suspend fun addToFav(wallpaperEntity: WallpaperEntity) {
        dao.insertIntoFav(wallpaperEntity = wallpaperEntity)
    }

    suspend fun removeFromFav(wallpaperEntity: WallpaperEntity) {
        dao.removeFav(wallpaperEntity = wallpaperEntity)
    }

    fun getAllWallpaperFav(): LiveData<List<WallpaperEntity>> {
        return dao.getAllWallpaper()
    }
}