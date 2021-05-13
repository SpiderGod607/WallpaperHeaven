package com.spidergod.wallpaperheaven.data.local.dto_to_entity

import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.data.models.WallpaperParcelable

class ParcelableConvertor {

    companion object {

        fun wallpaperEntityToParcelable(wallpaperEntity: WallpaperEntity): WallpaperParcelable {
            return wallpaperEntity.run {
                WallpaperParcelable(
                    id = id,
                    url = url,
                    source = source,
                    category = category,
                    dimension_x = dimension_x,
                    dimension_y = dimension_y,
                    resolution = resolution,
                    ratio = ratio,
                    fileType = fileType,
                    page = page,
                    cacheTag = cacheTag,
                    color = color,
                    urlHigh = urlHigh,
                    urlShort = urlShort
                )
            }
        }

        fun wallpaperParcelableToEntity(wallpaperParcelable: WallpaperParcelable): WallpaperEntity {
            return wallpaperParcelable.run {
                WallpaperEntity(
                    id = id,
                    url = url,
                    source = source,
                    category = category,
                    dimension_x = dimension_x,
                    dimension_y = dimension_y,
                    resolution = resolution,
                    ratio = ratio,
                    fileType = fileType,
                    page = page,
                    cacheTag = cacheTag,
                    color = color,
                    urlHigh = urlHigh,
                    urlShort = urlShort
                )
            }
        }
    }

}