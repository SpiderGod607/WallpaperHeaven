package com.spidergod.wallpaperheaven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen.WallpaperListScreen
import com.spidergod.wallpaperheaven.ui.theme.WallpaperHeavenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

                }
            }
        }
    }
}


