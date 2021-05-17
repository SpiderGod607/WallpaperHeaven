package com.spidergod.wallpaperheaven.ui.presentation.wallpaper_list_screen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.spidergod.wallpaperheaven.data.local.dto_to_entity.WallpaperListDtoToWallpaperEntity
import com.spidergod.wallpaperheaven.data.local.entity.WallpaperEntity
import com.spidergod.wallpaperheaven.repository.WallpaperRepository
import com.spidergod.wallpaperheaven.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperListViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private var currentPage = 1
    val wallpaperList = mutableStateOf<List<WallpaperEntity>>(listOf())
    val loadError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val endReached = mutableStateOf(false)

    fun loadWallpaperPagination(
        query: String = "",
        categories: String = "111",
        purity: String = "100",
        topRange: String = "1M",
        sorting: String = "toplist",
        order: String = "desc",
    ) {
        if (!isLoading.value) {
            viewModelScope.launch {
                isLoading.value = true
                val result = repository.getWallpaperList(
                    categories = categories,
                    purity = purity,
                    topRange = topRange,
                    sorting = sorting,
                    order = order,
                    query = query, page = currentPage
                )

                when (result) {
                    is Resource.Success -> {
                        endReached.value = currentPage >= result.data?.meta?.lastPage!!
                        wallpaperList.value += WallpaperListDtoToWallpaperEntity.wallpaperListToWallpaperList(
                            result.data,
                            currentPage,
                            "top"
                        )
                        currentPage++
                        loadError.value = ""
                        isLoading.value = false
                        Log.d("asd", "" + currentPage)
                    }
                    is Resource.Error -> {
                        loadError.value = result.message ?: "Unknown Error occurred"
                        isLoading.value = false
                    }
                }
            }
        }
    }

    val searchQuery = mutableStateOf("")
    var firstSearch = true


    //search filter
    val showSearchFilterDialog = mutableStateOf(false)
    val currentSelectedCategories = mutableStateOf("111")
    val sortBy = mutableStateOf("relevance")
    val orderBy = mutableStateOf("desc")

    fun doSearch() {
        if (currentSelectedCategories.value == "000") {
            currentSelectedCategories.value = "111"
        }

        loadWallpaperPagination(
            query = searchQuery.value, sorting = sortBy.value,
            categories = currentSelectedCategories.value,
            order = orderBy.value
        )
    }

    fun newQuery() {
        firstSearch = false
        wallpaperList.value = arrayListOf()
        currentPage = 1
        doSearch()
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }


}