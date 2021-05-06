package com.spidergod.wallpaperheaven.data.remote.response_models.wallpaper_list_model_response


import com.google.gson.annotations.SerializedName

data class WallpaperListReponseDto(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("meta")
    val meta: Meta?
)