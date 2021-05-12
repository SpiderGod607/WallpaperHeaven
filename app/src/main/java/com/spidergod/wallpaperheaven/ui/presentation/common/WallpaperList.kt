package com.spidergod.wallpaperheaven.ui.presentation.common

import android.graphics.Color
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.gotoWallpaperDetail
import com.spidergod.wallpaperheaven.util.loadPicture

@ExperimentalFoundationApi
@Composable
fun WallpaperList(
    navController: NavController,
    topPadding: Dp,
    wallpaperList: List<WallpaperEntity>,
    endReached: Boolean,
    loadError: String,
    isLoading: Boolean,
    pagingFunction: () -> Unit
) {

    val itemCount = wallpaperList.size

    LazyVerticalGrid(
        contentPadding = PaddingValues(10.dp, top = topPadding),
        cells = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
    ) {

        items(itemCount) {
            if (it >= itemCount - 1 && !endReached) {
                pagingFunction()
            }
            WallpaperListItem(
                wallpaper = wallpaperList[it], navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun WallpaperListItem(
    wallpaper: WallpaperEntity,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var defaultDominantColor by remember {
        mutableStateOf(Color.parseColor(wallpaper.color))
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10))
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                gotoWallpaperDetail(
                    wallpaper = wallpaper,
                    navController = navController
                )
            },

        ) {
        loadPicture(
            wallpaper.url,
            wallpaper.dimension_x,
            wallpaper.dimension_y,
            defaultDominantColor
        )?.let {
            Image(
                bitmap = it.asImageBitmap(), contentDescription = "wallpaper",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }

}
