package com.spidergod.wallpaperheaven.ui.presentation.wallpaper_favourite_screen

import androidx.lifecycle.ViewModel
import com.spidergod.wallpaperheaven.repository.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteWallpaperViewModel @Inject constructor(
    val repository: WallpaperRepository
) : ViewModel() {
    val favouriteWallpaperList = repository.getAllWallpaperFav()
}