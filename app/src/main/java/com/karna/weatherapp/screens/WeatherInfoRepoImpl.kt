package com.karna.weatherapp.screens

import com.karna.weatherapp.data.ApiState
import com.karna.weatherapp.data.WeatherApiService
import com.karna.weatherapp.data.currentTemperatureModels.WeatherData
import com.karna.weatherapp.data.forecastTemperatureModels.WeatherForecastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class WeatherInfoRepoImpl(private val weatherApiService: WeatherApiService) : WeatherInfoRepo {

    override suspend fun getCurrentWeather(): ApiState =
        suspendCoroutine { continuation ->
            weatherApiService.getCurrentWeather().enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            continuation.resume(ApiState.Success(it))
                        }
                            ?: continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    } else {
                        continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    continuation.resume(ApiState.Failure(t))
                }

            })
        }

    override suspend fun getForeCastWeather(): ApiState =
        suspendCoroutine { continuation ->
            weatherApiService.getForeCastWeather().enqueue(object : Callback<WeatherForecastResponse> {
                override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            continuation.resume(ApiState.Success(it))
                        }
                            ?: continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    } else {
                        continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    }
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    continuation.resume(ApiState.Failure(t))
                }

            })
        }


}