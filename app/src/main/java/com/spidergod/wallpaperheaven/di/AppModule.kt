package com.spidergod.wallpaperheaven.di

import com.spidergod.wallpaperheaven.data.remote.api.WallpaperApi
import com.spidergod.wallpaperheaven.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesWallpaperHeavenApi(): WallpaperApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(WallpaperApi::class.java)
    }

}