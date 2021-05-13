package com.spidergod.wallpaperheaven.ui.presentation.wallaper_detail_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spidergod.wallpaperheaven.data.local.dto_to_entity.WallpaperListDtoToWallpaperEntity
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.repository.WallpaperRepository
import com.spidergod.wallpaperheaven.util.Resource
import com.spidergod.wallpaperheaven.util.SetWallpaperUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperDetailViewModel @Inject constructor(
    val wallpaperRepository: WallpaperRepository,
    val setWallpaperUtil: SetWallpaperUtil
) :
    ViewModel() {
    val listOfSimilarWallpaper = mutableStateOf<List<WallpaperEntity>>(mutableListOf())
    val isError = mutableStateOf("")
    val currentBackgroundColor = mutableStateOf(
        listOf(
            Color(android.graphics.Color.parseColor("#FFFFFF")),
            Color(android.graphics.Color.BLACK)
        )
    )

    var currentSelectedWallpaper: WallpaperEntity? = null


    fun getSimilarWallpaper(wallpaperEntity: WallpaperEntity): MutableState<List<WallpaperEntity>> {

        viewModelScope.launch {
            currentBackgroundColor.value =
                listOf(
                    Color(android.graphics.Color.parseColor(wallpaperEntity.color)),
                    Color(android.graphics.Color.BLACK)
                )
            //listOfSimilarWallpaper.value.add(wallpaperEntity)
            val result = wallpaperRepository.getSimilarWallpaper(wallpaperEntity.id)

            when (result) {
                is Resource.Success -> {

                    val arrayList = ArrayList<WallpaperEntity>()
                    arrayList.apply {
                        add(wallpaperEntity)
                        addAll(
                            WallpaperListDtoToWallpaperEntity.wallpaperListToWallpaperList(
                                result.data!!, 1, wallpaperEntity.id
                            )
                        )
                    }

                    listOfSimilarWallpaper.value += arrayList

                    Log.d("sdf", "${listOfSimilarWallpaper.value.size}")
                    isError.value = ""

                }
                is Resource.Error -> {
                    isError.value = result.message ?: "Unknown error"
                }
            }

        }
        return listOfSimilarWallpaper
    }


    val setWallpaperMessage = mutableStateOf("")
    val showStackBar = mutableStateOf(false)
    fun setWallpaper() {
        viewModelScope.launch() {
            setWallpaperMessage.value = "Setting Wallpaper please wait.."
            showStackBar.value = true
            val setRes =
                currentSelectedWallpaper?.urlHigh?.let { setWallpaperUtil.setWallpaper(it) }
            if (setRes == true) {
                setWallpaperMessage.value = "Wallpaper has been set"
            } else {
                setWallpaperMessage.value = "Wallpaper has not been set"
            }
            showStackBar.value = false
        }

    }

}