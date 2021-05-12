package com.spidergod.wallpaperheaven.ui.presentation.wapaper_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.spidergod.wallpaperheaven.R
import com.spidergod.wallpaperheaven.ui.presentation.common.FilterChips
import com.spidergod.wallpaperheaven.ui.presentation.common.ThreeChipRow
import com.spidergod.wallpaperheaven.ui.presentation.common.TwoChipRow
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.SearchBar
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.WallpaperList
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.WallpaperListViewModel


@ExperimentalFoundationApi
@Composable
fun WallpaperSearchScreen(
    query: String, navController: NavController,
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {

    if (viewModel.firstSearch) {
        viewModel.searchQuery.value = query
        viewModel.newQuery()
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

    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {

            SearchScreenSearchBar()

            WallpaperList(
                navController = navController,
                topPadding = 50.dp,
                wallpaperList = wallpaperList,
                endReached = endReached,
                loadError = loadError,
                isLoading = isLoading,
                pagingFunction = {
                    viewModel.doSearch()
                }
            )
        }
    }

    FilterDialog()
}

@Composable
fun SearchScreenSearchBar(viewModel: WallpaperListViewModel = hiltNavGraphViewModel()) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth(),
    ) {
        Row {
            SearchBar(
                hint = "Search..",
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .fillMaxWidth(0.9f),
                onSearchFunction = {
                    viewModel.searchQuery.value = it
                    viewModel.newQuery()
                }
            )
            IconButton(
                onClick = { viewModel.showSearchFilterDialog.value = true },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "filter Icon",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}


@Composable
fun FilterDialog(
    viewModel: WallpaperListViewModel = hiltNavGraphViewModel()
) {
    if (viewModel.showSearchFilterDialog.value) {
        Dialog(onDismissRequest = {
            viewModel.newQuery()
            viewModel.showSearchFilterDialog.value = false
        }) {

            Surface(
                color = MaterialTheme.colors.primary
            ) {
                Column(
                    modifier = Modifier.padding(25.dp)
                ) {
                    Text(
                        text = "Categories", color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                    Row {
                        FilterChips(
                            name = "General",
                            isEnabled = viewModel.currentSelectedCategories.value[0] == '1',
                            modifier = Modifier.clip(
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    bottomStart = 12.dp
                                )
                            )
                        ) {
                            if (viewModel.currentSelectedCategories.value[0] == '1') {
                                viewModel.currentSelectedCategories.value =
                                    changeChar('0', viewModel.currentSelectedCategories.value, 0)
                            } else {
                                viewModel.currentSelectedCategories.value =
                                    changeChar('1', viewModel.currentSelectedCategories.value, 0)
                            }
                        }
                        FilterChips(
                            name = "Anime",
                            isEnabled = viewModel.currentSelectedCategories.value[1] == '1',
                            modifier = Modifier
                        ) {
                            if (viewModel.currentSelectedCategories.value[1] == '1') {
                                viewModel.currentSelectedCategories.value =
                                    changeChar('0', viewModel.currentSelectedCategories.value, 1)
                            } else {
                                viewModel.currentSelectedCategories.value =
                                    changeChar('1', viewModel.currentSelectedCategories.value, 1)
                            }
                        }
                        FilterChips(
                            name = "People",
                            isEnabled = viewModel.currentSelectedCategories.value[2] == '1',
                            modifier = Modifier.clip(
                                RoundedCornerShape(
                                    bottomEnd = 12.dp,
                                    topEnd = 12.dp
                                )
                            )
                        ) {
                            if (viewModel.currentSelectedCategories.value[2] == '1') {
                                viewModel.currentSelectedCategories.value =
                                    changeChar('0', viewModel.currentSelectedCategories.value, 2)
                            } else {
                                viewModel.currentSelectedCategories.value =
                                    changeChar('1', viewModel.currentSelectedCategories.value, 2)
                            }
                        }
                    }

                    Text(
                        text = "Sort By",
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                        color = Color.White
                    )
                    ThreeChipRow(
                        chip1 = "Relevance",
                        chip2 = "Random",
                        chip3 = "Date",
                        onClick = {
                            viewModel.sortBy.value = it
                        },
                        isEnabled = {
                            viewModel.sortBy.value == it
                        }
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    ThreeChipRow(
                        chip1 = "Views",
                        chip2 = "Favorites",
                        chip3 = "Toplist",
                        onClick = {
                            viewModel.sortBy.value = it
                        },
                        isEnabled = {
                            viewModel.sortBy.value == it
                        }
                    )

                    Spacer(modifier = Modifier.size(5.dp))
                    FilterChips(
                        name = "Hot",
                        isEnabled = viewModel.sortBy.value == "hot",
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    12.dp
                                )
                            )
                            .fillMaxWidth()
                    ) {
                        viewModel.sortBy.value = "hot"
                    }
                    Text(
                        text = "Order by",
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                        color = Color.White
                    )

                    TwoChipRow(
                        chip1 = "Desc",
                        chip2 = "Asc",
                        onClick = {
                            viewModel.orderBy.value = it
                        },
                        isEnabled = {
                            viewModel.orderBy.value == it
                        }
                    )
                    Text(
                        text = "Apply",
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 5.dp)
                            .clickable {
                                viewModel.newQuery()
                            },
                        color = Color.White,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

fun changeChar(char: Char, string: String, index: Int): String {
    return string.substring(0, index) + char + string.substring(index + 1)
}