package com.spidergod.wallpaperheaven.di

import com.spidergod.wallpaperheaven.data.remote.api.WallpaperApi
import com.spidergod.wallpaperheaven.repository.WallpaperRepository
import com.spidergod.wallpaperheaven.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRepo(
        api: WallpaperApi
    ) = WallpaperRepository(api)

    @Singleton
    @Provides
    fun providesWallpaperHeavenApi(): WallpaperApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor = interceptor).build())
            .baseUrl(BASE_URL).build().create(WallpaperApi::class.java)
    }

}