package com.spidergod.wallpaperheaven.ui.presentation.wallaper_detail_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.google.accompanist.glide.rememberGlidePainter
import com.spidergod.wallpaperheaven.R
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.ui.presentation.composeutil.Pager
import com.spidergod.wallpaperheaven.ui.presentation.composeutil.PagerState
import com.spidergod.wallpaperheaven.util.ShareLinkWithOtherApps
import com.spidergod.wallpaperheaven.util.handleDownload
import com.spidergod.wallpaperheaven.util.lastPathComponent


@Composable
fun WallpaperDetailScreen(
    wallpaper: WallpaperEntity,
    viewModel: WallpaperDetailViewModel = hiltNavGraphViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val backColor by remember {
        viewModel.currentBackgroundColor
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(backColor),
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            WallpaperPager(wallpaperEntity = wallpaper)
        }

        item {
            Row {
                Column {
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        onClick = {
                            ShareLinkWithOtherApps(
                                context = context, "Checkout this cool wallpaper I found \n " +
                                        "WallHeaven link -> ${viewModel.currentSelectedWallpaper?.urlShort} \n" +
                                        "Source -> ${viewModel.currentSelectedWallpaper?.source}"
                            )
                        },
                        modifier = Modifier
                            .size(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black
                        )

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "Share wallpaper",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = {
                        handleDownload(
                            context = context,
                            fromUrl = viewModel.currentSelectedWallpaper?.urlHigh,
                            toFilename = viewModel.currentSelectedWallpaper?.urlHigh?.lastPathComponent
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = CircleShape)
                        .background(Color.White),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = "Download button",
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Column {
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        onClick = { viewModel.setWallpaper() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                        ),
                        modifier = Modifier
                            .size(50.dp)
                            .clip(shape = CircleShape),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_set_wallpaper),
                            contentDescription = "Set wallpaper",
                            modifier = Modifier
                                .shadow(
                                    shape = CircleShape,
                                    elevation = 0.dp
                                )
                                .fillMaxSize()
                        )
                    }
                }

            }
        }
    }

    if (viewModel.showStackBar.value) {
        Snackbar(
            content = {
                Text(text = viewModel.setWallpaperMessage.value)
            }
        )
    }

}

@Composable
fun WallpaperPager(
    wallpaperEntity: WallpaperEntity,
    viewModel: WallpaperDetailViewModel = hiltNavGraphViewModel()
) {
    val wallpapers by remember {
        viewModel.getSimilarWallpaper(wallpaperEntity)
    }

    if (wallpapers.isNotEmpty()) {
        val pageState: PagerState = run {
            remember {
                PagerState(0, 0, wallpapers.size - 1)
            }
        }
        Pager(state = pageState, modifier = Modifier.height(645.dp)) {
            val wallpaper = wallpapers[page]
            val isSelected = pageState.currentPage == page

            WallpaperPagerItem(
                wallpaperEntity = wallpaper,
                isSelected = isSelected
            )
        }
    }
}

@Composable
fun WallpaperPagerItem(
    wallpaperEntity: WallpaperEntity,
    isSelected: Boolean,
    viewModel: WallpaperDetailViewModel = hiltNavGraphViewModel()
) {
    val animateHeight = animateDpAsState(if (isSelected) 645.dp else 360.dp).value
    val animateWidth = animateDpAsState(targetValue = if (isSelected) 340.dp else 320.dp).value
    val animateElevation = if (isSelected) 12.dp else 2.dp

    if (isSelected) {
        viewModel.currentBackgroundColor.value = listOf(
            Color(android.graphics.Color.parseColor(wallpaperEntity.color)),
            Color(android.graphics.Color.BLACK)
        )
        viewModel.currentSelectedWallpaper = wallpaperEntity
    }

    Card(
        elevation = animateDpAsState(targetValue = animateElevation).value,
        modifier = Modifier
            .width(animateWidth)
            .height(animateHeight)
            .padding(24.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(android.graphics.Color.parseColor(wallpaperEntity.color)),
        contentColor = Color(android.graphics.Color.parseColor(wallpaperEntity.color))
    ) {
        Image(
            painter = rememberGlidePainter(request = wallpaperEntity.urlHigh),
            contentDescription = "Wallpaper",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
    }
}


