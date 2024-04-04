package com.karna.weatherapp.data.forecastTemperatureModels

import com.google.gson.annotations.SerializedName

data class ForecastMainInfo(
    @SerializedName("temp") val temperature: Double = 0.0,
    @SerializedName("humidity") val humidity: Int = 0
)
