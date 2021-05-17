package com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.spidergod.wallpaperheaven.R
import com.spidergod.wallpaperheaven.data.local.dto_to_entity.ParcelableConvertor
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.ui.presentation.common.NoInternetAnimation
import com.spidergod.wallpaperheaven.ui.presentation.common.PaperPlaneLoading
import com.spidergod.wallpaperheaven.ui.presentation.common.SystemBarColor
import com.spidergod.wallpaperheaven.ui.presentation.common.WallpaperList
import com.spidergod.wallpaperheaven.ui.presentation.common.bottomnav.BottomNav
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun WallpaperListScreen(
    navController: NavController,
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {
    val toolbarHeight = 220.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    SystemBarColor(color = MaterialTheme.colors.primary)

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

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
        viewModel.loadWallpaperPagination()
    }

    Scaffold(
        content = { innerPadding ->
            Surface(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .nestedScroll(nestedScrollConnection)
                ) {

                    WallpaperList(
                        navController = navController,
                        topPadding = toolbarHeight,
                        wallpaperList = wallpaperList,
                        endReached = endReached,
                        loadError = loadError,
                        isLoading = isLoading,
                        pagingFunction = {
                            viewModel.loadWallpaperPagination()
                        }
                    )

                    SearchBarWithImageBackground(
                        navController,
                        modifier = Modifier
                            .height(toolbarHeight)
                            .offset {
                                IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt())
                            }
                    )
                }
                if (wallpaperList.isEmpty() && isLoading) {
                    PaperPlaneLoading(modifier = Modifier)
                }

                if (!isLoading && wallpaperList.isEmpty() && loadError.isNotEmpty()) {
                    NoInternetAnimation(modifier = Modifier.padding(top = 25.dp))
                }
            }
        },
        bottomBar = {
            BottomNav(navController)
        }
    )
}

@Composable
fun SearchBarWithImageBackground(
    navController: NavController,
    modifier: Modifier,
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Image(
            painter = painterResource(id = R.drawable.search_back),
            contentDescription = "Search background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        SearchBar(
            hint = "Search..",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 15.dp, vertical = 20.dp),
            onSearchFunction = { query ->
                goToWallpaperSearchScreen(query = query, navController = navController)
            }
        )
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearchFunction: (String) -> Unit = {},
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    val alpha: Float by animateFloatAsState(targetValue = if (isHintDisplayed) 0.5f else 1f)

    Box(modifier = modifier.graphicsLayer(alpha = alpha)) {
        BasicTextField(
            value = viewModel.searchQuery.value,
            onValueChange = {
                viewModel.searchQuery.value = it
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it != FocusState.Active
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchFunction(viewModel.searchQuery.value)
                }
            )
        )

        if (isHintDisplayed && viewModel.searchQuery.value.isEmpty()) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)

            )
        }
    }
}

fun gotoWallpaperDetail(wallpaper: WallpaperEntity, navController: NavController) {
    navController.currentBackStackEntry?.arguments?.putParcelable(
        "wallpaper",
        ParcelableConvertor.wallpaperEntityToParcelable(wallpaperEntity = wallpaper)
    )
    navController.navigate("wallpaper_detail")
}

fun goToWallpaperSearchScreen(query: String, navController: NavController) {
    navController.navigate("wallpaper_search_screen/$query")
}