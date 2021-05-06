package com.spidergod.wallpaperheaven.data.local.dto_to_entity

import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.data.remote.response_models.wallpaper_list_model_response.WallpaperListReponseDto

class WallpaperListDtoToWallpaperEntity {
    companion object {
        fun wallpaperListToWallpaperList(
            wallpaperListResponseDto: WallpaperListReponseDto,
            page: Int,
            cacheTag: String
        ): List<WallpaperEntity> {
            val data = wallpaperListResponseDto.data

            return data!!.map {
                WallpaperEntity(
                    id = it.id!!,
                    urlHigh = it.path!!,
                    source = it.source!!,
                    category = it.category!!,
                    dimension_x = it.dimensionX!!,
                    dimension_y = it.dimensionY!!,
                    resolution = it.resolution!!,
                    ratio = it.ratio!!,
                    fileType = it.fileType!!,
                    page = page,
                    cacheTag = cacheTag,
                    color = it.colors?.get(0) ?: "#FFFFFF",
                    url = it.thumbs?.original ?: "sdf"
                )
            }

        }
    }
}

/*









val cacheTag: String*/
