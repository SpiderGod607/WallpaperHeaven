package com.spidergod.wallpaperheaven.ui.presentation.common.bottomnav

import com.spidergod.wallpaperheaven.R
import com.spidergod.wallpaperheaven.util.Constants.FAVOURITE
import com.spidergod.wallpaperheaven.util.Constants.NEWEST
import com.spidergod.wallpaperheaven.util.Constants.WALLPAPER_TOP_LIST


sealed class Screen(val route: String, val label: String, val icon: Int) {
    object Top : Screen(WALLPAPER_TOP_LIST, "Top Wallpaper", R.drawable.ic_top)
    object Favourite : Screen(FAVOURITE, "Favourite", R.drawable.ic_fav)
    object Newest : Screen(NEWEST, "Newest", R.drawable.ic_new)
}

