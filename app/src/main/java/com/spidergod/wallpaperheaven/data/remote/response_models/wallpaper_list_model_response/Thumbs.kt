package com.spidergod.wallpaperheaven.data.remote.response_models.wallpaper_list_model_response


import com.google.gson.annotations.SerializedName

data class Thumbs(
    @SerializedName("large")
    val large: String?,
    @SerializedName("original")
    val original: String?,
    @SerializedName("small")
    val small: String?
)