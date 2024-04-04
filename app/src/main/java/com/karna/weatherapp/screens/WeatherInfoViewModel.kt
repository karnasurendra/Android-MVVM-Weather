package com.karna.weatherapp.screens

import androidx.lifecycle.ViewModel
import com.karna.weatherapp.data.ApiState
import com.karna.weatherapp.data.currentTemperatureModels.WeatherData
import com.karna.weatherapp.data.forecastTemperatureModels.ForecastItem
import com.karna.weatherapp.data.forecastTemperatureModels.WeatherForecastResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(private val repo: WeatherInfoRepo) : ViewModel() {

    private val _currentWeatherInfo = MutableSharedFlow<WeatherData>()
    val currentWeatherInfo = _currentWeatherInfo.asSharedFlow()

    private val _nextFourDaysForecast = MutableSharedFlow<List<ForecastItem>>()
    val nextFourDaysForecast = _nextFourDaysForecast.asSharedFlow()

    private val _failureCase = MutableSharedFlow<Throwable>()
    val failureCase = _failureCase.asSharedFlow()

    // Function to get both Current  and Forecast weather info  from server
    fun getWeatherInformation() {

        CoroutineScope(Dispatchers.IO).launch {

            // Helps to get current weather info
            val currentWeatherApiStateDeferred: Deferred<ApiState> =
                async { repo.getCurrentWeather() }
            // Helps to get forecast weather info
            val forecastWeatherApiStateDeferred: Deferred<ApiState> =
                async { repo.getForeCastWeather() }


            val currentWeatherApiState = currentWeatherApiStateDeferred.await()
            val forecastWeatherApiState = forecastWeatherApiStateDeferred.await()

            when (currentWeatherApiState) {
                is ApiState.Failure -> {
                    _failureCase.emit(currentWeatherApiState.throwable)
                }

                is ApiState.Success -> {
                    _currentWeatherInfo.emit(currentWeatherApiState.data as WeatherData)
                }
            }

            when (forecastWeatherApiState) {
                is ApiState.Failure -> {
                    _failureCase.emit(forecastWeatherApiState.throwable)
                }

                is ApiState.Success -> {
                    val forecastWeatherData =
                        forecastWeatherApiState.data as WeatherForecastResponse

                    // Get the current date
                    val currentDate = LocalDateTime.now().dayOfMonth

                    // Filter the forecast data to include only the next four days' forecast
                    val nextFourDaysForecast =
                        forecastWeatherData.forecastList.filter { forecastItem ->
                            // Extract the date from the forecast item
                            val forecastDate = LocalDateTime.parse(
                                forecastItem.dateTime,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            ).dayOfMonth

                            // Check if the forecast date is within the next four days
                            forecastDate in currentDate + 1..currentDate + 4
                        }.distinctBy { forecastItem ->
                            // Use distinctBy to remove duplicate entries based on the date
                            LocalDateTime.parse(
                                forecastItem.dateTime,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            ).dayOfMonth
                        }
                    _nextFourDaysForecast.emit(nextFourDaysForecast)
                }
            }

        }

    }

    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    private fun getDayOfWeek(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return outputFormat.format(date!!)
    }

    fun getDayAndTemperature(forecastItem: ForecastItem): Pair<String, String> {
        val day = getDayOfWeek(forecastItem.dateTime)
        val temp = "${(kelvinToCelsius(forecastItem.mainInfo.temperature)).toInt()}\u00B0 C"
        return Pair(day, temp)
    }


}