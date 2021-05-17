package com.spidergod.wallpaperheaven.ui.presentation.wallpapr_newst

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.spidergod.wallpaperheaven.ui.presentation.common.*
import com.spidergod.wallpaperheaven.ui.presentation.common.bottomnav.BottomNav
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.WallpaperListViewModel

@ExperimentalFoundationApi
@Composable
fun NewestWallpaper(
    navController: NavController,
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {
    val wallpaperList by remember {
        viewModel.wallpaperList
    }

    val endReached by remember {
        viewModel.endReached
    }

    val loadError by remember {
        viewModel.loadError
    }

    val isLoading by remember {
        viewModel.isLoading
    }

    if (!isLoading && loadError.isEmpty() && !endReached && wallpaperList.isEmpty()) {
        viewModel.loadWallpaperPagination(sorting = "date_added")
    }

    SystemBarColor(color = MaterialTheme.colors.primary)

    Scaffold(
        bottomBar = {
            BottomNav(navController)
        },
        topBar = {
            TopBar("Newest Wallpaper")
        }
    ) { innerPadding ->

        Surface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            WallpaperList(
                navController = navController,
                topPadding = 0.dp,
                wallpaperList = wallpaperList,
                endReached = endReached,
                loadError = loadError,
                isLoading = isLoading,
                pagingFunction = {
                    viewModel.loadWallpaperPagination(sorting = "date_added")
                }
            )

            if (wallpaperList.isEmpty() && isLoading) {
                PaperPlaneLoading(modifier = Modifier)
            }

            if (!isLoading && wallpaperList.isEmpty() && loadError.isNotEmpty()) {
                NoInternetAnimation(modifier = Modifier)
            }

        }
    }
}