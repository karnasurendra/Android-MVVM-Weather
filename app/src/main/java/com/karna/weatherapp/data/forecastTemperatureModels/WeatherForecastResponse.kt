package com.karna.weatherapp.data.forecastTemperatureModels

import com.google.gson.annotations.SerializedName
import com.karna.weatherapp.data.forecastTemperatureModels.ForecastItem

data class WeatherForecastResponse(
    @SerializedName("list") val forecastList: List<ForecastItem> = emptyList()
)
