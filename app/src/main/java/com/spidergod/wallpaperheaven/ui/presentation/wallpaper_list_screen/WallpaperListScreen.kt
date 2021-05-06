package com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.util.loadPicture


@Composable
fun WallpaperListScreen(
    navController: NavController
) {
    /* Surface(
         color = MaterialTheme.colors.background,
         modifier = Modifier.fillMaxSize()
     ) {
        *//* Column {
            SearchBar()
        }
        Spacer(modifier = Modifier.height(16.dp))
        WallpaperList(navController = navController)*//*
    }*/

    WallpaperList(navController = navController)
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {

    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                //onSearch(it)
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
                }
        )

        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

    }
}

@Composable
fun WallpaperList(
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

    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        val itemCount = if (wallpaperList.size % 2 == 0) {
            wallpaperList.size / 2
        } else {
            wallpaperList.size / 2 + 1
        }
        items(itemCount) {
            if (it >= itemCount - 1 && !endReached) {
                viewModel.loadWallpaperPagination()
            }
            WallpaperRow(
                rowIndex = it,
                wallpaperList = wallpaperList,
                navController = navController
            )
        }
    }

}


@Composable
fun WallpaperRow(
    rowIndex: Int,
    wallpaperList: List<WallpaperEntity>,
    navController: NavController
) {

    Column {
        Row {
            WallpaperListItem(
                wallpaper = wallpaperList[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (wallpaperList.size >= rowIndex * 2 + 2) {
                WallpaperListItem(
                    wallpaper = wallpaperList[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun WallpaperListItem(
    wallpaper: WallpaperEntity,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {
    var defaultDominantColor by remember {
        mutableStateOf(android.graphics.Color.parseColor(wallpaper.color))
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10))
            .clip(RoundedCornerShape(10.dp))
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

