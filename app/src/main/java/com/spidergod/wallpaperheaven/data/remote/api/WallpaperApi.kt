package com.spidergod.wallpaperheaven.data.remote.api

import com.spidergod.wallpaperheaven.data.remote.response_models.wallpaper_list_model_response.WallpaperListReponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApi {

    @GET("search")
    suspend fun getWallpaperList(
        @Query("categories") categories: String = "111",
        @Query("purity") purity: String = "110",
        @Query("topRange") topRange: String = "1M",
        @Query("sorting") sorting: String = "toplist",
        @Query("order") order: String = "desc",
        @Query("page") page: Int
    ): WallpaperListReponseDto

    @GET("search")
    suspend fun getSimilarWallpaper(
        @Query("q") query: String
    ): WallpaperListReponseDto


}