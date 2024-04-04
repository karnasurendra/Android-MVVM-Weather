package com.karna.weatherapp.data.forecastTemperatureModels

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("main") val main: String = String(),
    @SerializedName("description") val description: String = String()
)
