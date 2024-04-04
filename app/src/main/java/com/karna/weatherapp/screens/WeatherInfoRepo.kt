package com.karna.weatherapp.screens

import com.karna.weatherapp.data.ApiState

interface WeatherInfoRepo {

    suspend fun getCurrentWeather(): ApiState

    suspend fun getForeCastWeather(): ApiState

}