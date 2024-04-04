package com.karna.weatherapp.data.currentTemperatureModels

import com.google.gson.annotations.SerializedName


data class MainInfo(
    @SerializedName("temp") val temperature: Double = 0.0
)
