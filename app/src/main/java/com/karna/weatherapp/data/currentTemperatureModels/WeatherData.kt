package com.karna.weatherapp.data.currentTemperatureModels

import com.google.gson.annotations.SerializedName
import com.karna.weatherapp.data.currentTemperatureModels.MainInfo

data class WeatherData(
    @SerializedName("name") val cityName: String  = String(),
    @SerializedName("main") val mainInfo: MainInfo = MainInfo()
)
