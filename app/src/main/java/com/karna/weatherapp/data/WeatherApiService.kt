package com.karna.weatherapp.data

import com.karna.weatherapp.utils.AppConstants
import com.karna.weatherapp.data.currentTemperatureModels.WeatherData
import com.karna.weatherapp.data.forecastTemperatureModels.WeatherForecastResponse
import retrofit2.Call
import retrofit2.http.GET

interface WeatherApiService {

    @GET(AppConstants.Apis.CURRENT_WEATHER_API)
    fun getCurrentWeather(): Call<WeatherData>

    @GET(AppConstants.Apis.FORECAST_WEATHER_API)
    fun getForeCastWeather(): Call<WeatherForecastResponse>
}