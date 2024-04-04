package com.karna.weatherapp.di

import com.karna.weatherapp.data.WeatherApiService
import com.karna.weatherapp.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(): WeatherApiService = Retrofit.Builder()
        .baseUrl(AppConstants.Apis.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient()).build()
        .create(WeatherApiService::class.java)

}