package com.karna.weatherapp.data.forecastTemperatureModels

import com.google.gson.annotations.SerializedName

data class ForecastItem(
    @SerializedName("dt") val timestamp: Long = 0,
    @SerializedName("main") val mainInfo: ForecastMainInfo = ForecastMainInfo(),
    @SerializedName("weather") val weather: List<WeatherInfo> = emptyList(),
    @SerializedName("dt_txt") val dateTime: String = String()
)
