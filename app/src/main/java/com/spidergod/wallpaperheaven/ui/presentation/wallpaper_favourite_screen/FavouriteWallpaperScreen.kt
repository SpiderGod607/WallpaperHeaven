package com.spidergod.wallpaperheaven.ui.presentation.wallpaper_favourite_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.spidergod.wallpaperheaven.ui.presentation.common.Ghost
import com.spidergod.wallpaperheaven.ui.presentation.common.SystemBarColor
import com.spidergod.wallpaperheaven.ui.presentation.common.TopBar
import com.spidergod.wallpaperheaven.ui.presentation.common.WallpaperList
import com.spidergod.wallpaperheaven.ui.presentation.common.bottomnav.BottomNav


@ExperimentalFoundationApi
@Composable
fun FavouriteWallpaperScreen(
    navController: NavController,
    viewModel: FavouriteWallpaperViewModel = hiltNavGraphViewModel()
) {
    val wallpaperList by viewModel.favouriteWallpaperList.observeAsState()
    SystemBarColor(color = MaterialTheme.colors.primary)
    Surface(
        color = MaterialTheme.colors.secondary
    ) {
        Scaffold(
            topBar = {
                TopBar(name = "Favourites")
            },
            bottomBar = {
                BottomNav(navController = navController)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colors.secondary)
            ) {
                wallpaperList?.let { it1 ->
                    WallpaperList(
                        navController = navController,
                        topPadding = 0.dp,
                        wallpaperList = it1 ?: arrayListOf(),
                        endReached = false,
                        loadError = "nan",
                        isLoading = false
                    ) {

                    }
                    if (wallpaperList == null || wallpaperList!!.isEmpty()) {
                        Ghost(modifier = Modifier.fillMaxSize())
                    }

                }
            }
        }
    }
}