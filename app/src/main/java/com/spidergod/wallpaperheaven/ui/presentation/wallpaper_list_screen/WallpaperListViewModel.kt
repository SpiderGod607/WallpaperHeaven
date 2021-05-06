package com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.spidergod.wallpaperheaven.data.local.dto_to_entity.WallpaperListDtoToWallpaperEntity
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.repository.WallpaperRepository
import com.spidergod.wallpaperheaven.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperListViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private var currentPage = 1
    val wallpaperList = mutableStateOf<List<WallpaperEntity>>(listOf())
    val loadError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)

    init {
        loadWallpaperPagination()
    }

    fun loadWallpaperPagination() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getWallpaperList(page = currentPage)

            when (result) {
                is Resource.Success -> {
                    endReached.value = currentPage >= result.data?.meta?.lastPage!!
                    wallpaperList.value += WallpaperListDtoToWallpaperEntity.wallpaperListToWallpaperList(
                        result.data,
                        currentPage,
                        "top"
                    )
                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadError.value = result.message ?: "Unknown Error occurred"
                    isLoading.value = false
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}