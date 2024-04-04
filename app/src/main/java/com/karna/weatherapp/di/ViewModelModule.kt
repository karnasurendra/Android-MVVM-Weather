package com.karna.weatherapp.di

import com.karna.weatherapp.data.WeatherApiService
import com.karna.weatherapp.screens.WeatherInfoRepo
import com.karna.weatherapp.screens.WeatherInfoRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideWeatherRepo(weatherApiService: WeatherApiService): WeatherInfoRepo {
        return WeatherInfoRepoImpl(weatherApiService)
    }

}