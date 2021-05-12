package com.spidergod.wallpaperheaven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.spidergod.wallpaperheaven.data.local.dto_to_entity.ParcelableConvertor
import com.spidergod.wallpaperheaven.data.models.WallpaperParcelable
import com.spidergod.wallpaperheaven.ui.presentation.wallaper_detail_screen.WallpaperDetailScreen
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.WallpaperListScreen
import com.spidergod.wallpaperheaven.ui.presentation.wapaper_search_screen.WallpaperSearchScreen
import com.spidergod.wallpaperheaven.ui.theme.WallpaperHeavenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperHeavenTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "wallpaper_top_list_screen"
                ) {
                    composable("wallpaper_top_list_screen") {
                        WallpaperListScreen(navController)
                    }
                    composable("wallpaper_detail") {
                        val wallpaper =
                            navController.previousBackStackEntry?.arguments?.getParcelable<WallpaperParcelable>(
                                "wallpaper"
                            )
                        WallpaperDetailScreen(
                            wallpaper = ParcelableConvertor.wallpaperParcelableToEntity(wallpaper!!),
                            navController = navController
                        )
                    }
                    composable(
                        "wallpaper_search_screen/{query}",
                        arguments = listOf(navArgument("query") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        navBackStackEntry.arguments?.getString("query")
                            ?.let {
                                WallpaperSearchScreen(
                                    query = it,
                                    navController = navController
                                )
                            }
                    }
                }
            }
        }
    }
}


